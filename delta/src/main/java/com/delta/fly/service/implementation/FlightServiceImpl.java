package com.delta.fly.service.implementation;

import com.delta.fly.dto.FlightDTO;
import com.delta.fly.dto.PlaceAndTimeDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.repository.FlightRepository;
import com.delta.fly.service.abstraction.AirplaneService;
import com.delta.fly.service.abstraction.FlightService;
import com.delta.fly.service.abstraction.PlaceAndTimeService;
import com.delta.fly.service.abstraction.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private PlaceAndTimeService placeAndTimeService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public List<Flight> findAll() {
        return flightRepository.findFlightsByDeletedFalse();
    }

    @Override
    public Flight getOne(Long id) throws ObjectNotFoundException {
        return flightRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Flight with ID: " + id + " not found!"));
    }

    @Override
    public Flight create(FlightDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<Flight> flight;
        Optional<AirlineCompany> company;
        Optional<Airplane> airplane;
        try {
            company = Optional.ofNullable(userDetailsService.getAdmin().getAirlineCompany());
            if (!company.isPresent()) {
                throw new ObjectNotFoundException("Airline company not found!");
            }
            airplane = Optional.ofNullable(airplaneService.getOne(dto.getAirplaneID()));
            if (!airplane.isPresent()) {
                throw new ObjectNotFoundException("Airplane with ID: " + dto.getAirplaneID() + " not found!");
            }
            if (!company.get().getAirplanes().contains(airplane.get())) {
                throw new InvalidInputException("Airplane doesn't belong to this company.");
            }
            PlaceAndTime dep = new PlaceAndTime(dto.getDeparture().getThePlace(), dto.getDeparture().getTheTime());
            PlaceAndTime arr = new PlaceAndTime(dto.getArrival().getThePlace(), dto.getArrival().getTheTime());
            List<PlaceAndTime> pats = new ArrayList<>();
            for (PlaceAndTimeDTO pat : dto.getTransfers()) {
                pats.add(new PlaceAndTime(pat.getThePlace(), pat.getTheTime()));
            }
            checks(arr, dep, pats, company.get(), airplane.get());
            flight = Optional.ofNullable(new Flight());
            flight.get().setDeleted(false);
            flight.get().setTravelTime(dto.getTravelTime());
            flight.get().setDistance(dto.getDistance());
            PlaceAndTime departure = placeAndTimeService.create(dto.getDeparture());
            flight.get().setDeparture(departure);
            PlaceAndTime arrival = placeAndTimeService.create(dto.getArrival());
            flight.get().setArrival(arrival);
            flight.get().setTransfers(new ArrayList<>());
            flight.get().setAirplane(airplane.get());
            flight.get().setAirlineCompany(company.get());
            if (dto.getTransfers().size() > 0) {
                for (PlaceAndTimeDTO pat : dto.getTransfers()) {
                    PlaceAndTime transfer = placeAndTimeService.create(pat);
                    flight.get().getTransfers().add(transfer);
                }
            }
            flight.get().setDeleted(false);
            if (dto.getGenerateTickets()) {
                for (Seat s : flight.get().getAirplane().getSeats()) {
                    ticketService.create(flight.get(), s);
                }
            } else {
                flight.get().setTickets(new ArrayList<>());
            }
            return flightRepository.save(flight.get());
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        }
    }

    @Override
    public Flight update(Flight flight) throws ObjectNotFoundException, InvalidInputException {
        Optional<Flight> uFlight;
        Optional<Airplane> airplane;
        Optional<AirlineCompany> company;
        try {
            uFlight = Optional.ofNullable(getOne(flight.getId()));
            airplane = Optional.ofNullable(airplaneService.getOne(flight.getAirplane().getId()));
            company = Optional.ofNullable(userDetailsService.getAdmin().getAirlineCompany());
            if (uFlight.isPresent() && airplane.isPresent() && company.isPresent()) {
                if (!company.get().getFlights().contains(uFlight.get())) {
                    throw new InvalidInputException("Admin's company doesn't have this flight.");
                }
                if (!company.get().getAirplanes().contains(airplane.get())) {
                    System.out.println(userDetailsService.getAdmin().getAirlineCompany().getAirplanes());
                    throw new InvalidInputException("Admin's company doesn't have this airplane.");
                }
                checks(flight.getArrival(), flight.getDeparture(), flight.getTransfers(), company.get(), airplane.get());
                uFlight.get().setAirlineCompany(company.get());
                uFlight.get().setAirplane(airplane.get());
                uFlight.get().setArrival(placeAndTimeService.update(flight.getArrival()));
                uFlight.get().setDeparture(placeAndTimeService.update(flight.getDeparture()));
                uFlight.get().setDistance(flight.getDistance());
                uFlight.get().setTickets(flight.getTickets());
                uFlight.get().setTransfers(flight.getTransfers());
                uFlight.get().setTravelTime(flight.getTravelTime());
                uFlight.get().setDeleted(flight.getDeleted());
                return flightRepository.save(uFlight.get());
            } else {
                throw new ObjectNotFoundException("Bad ID!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Flight with ID: " + flight.getId() + " not found!", ex);
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Flight flight = getOne(id);
            if (!userDetailsService.getAdmin().getAirlineCompany().getFlights().contains(flight)) {
                throw new ObjectNotFoundException("This admin's company doesn't have that flight!");
            }
            // Checking if tickets were sold.
            for (Ticket ticket : flight.getTickets()) {
                if (ticket.getPassenger() != null) {
                    throw new ObjectNotFoundException("Tickets were sold for this flight!");
                }
            }
            for (Ticket ticket : new ArrayList<>(flight.getTickets())) {
                ticketService.delete(ticket.getId());
            }
            flight.setDeleted(true);
            flightRepository.save(flight);
            return flight.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    private void checks(PlaceAndTime arrival, PlaceAndTime departure, List<PlaceAndTime> transfers, AirlineCompany company, Airplane airplane) throws InvalidInputException {
        if (!company.getAirplanes().contains(airplane) || !airplane.getAirlineCompany().equals(company)) {
            throw new InvalidInputException("Airline company doesn't have an airplane with ID: " + airplane.getId() + "!");
        }
        if (!company.getDestinations().contains(arrival.getThePlace())) {
            throw new InvalidInputException("Airline company doesn't operate in the city of: " + arrival.getThePlace() + ".");
        }
        if (!company.getDestinations().contains(departure.getThePlace())) {
            throw new InvalidInputException("Airline company doesn't operate in the city of: " + departure.getThePlace() + ".");
        }
        if (departure.getTheTime().after(arrival.getTheTime())) {
            throw new InvalidInputException("Departure date can't be after arrival date.");
        }
//        for (Flight flight: company.getFlights()) {
//            if (flight.getAirplane().equals(airplane)) {
//                if (flight.getDeparture().getTheTime().before(departure.getTheTime()) ||
//                    flight.getArrival().getTheTime().after(departure.getTheTime())) {
//
//                }
//            }
//        }
        if (transfers.size() > 0) {
            for (PlaceAndTime pat : transfers) {
                if (!company.getDestinations().contains(pat.getThePlace())) {
                    throw new InvalidInputException("Airline company doesn't operate in the city of: " + pat.getThePlace() + ".");
                }
                if (pat.getTheTime().before(departure.getTheTime())) {
                    throw new InvalidInputException("Transfer date can't be before departure date.");
                }
                if (pat.getTheTime().after(arrival.getTheTime())) {
                    throw new InvalidInputException("Transfer date can't be after arrival date.");
                }
            }
        }
    }

}
