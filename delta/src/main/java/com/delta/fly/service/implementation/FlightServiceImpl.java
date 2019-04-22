package com.delta.fly.service.implementation;

import com.delta.fly.dto.FlightDTO;
import com.delta.fly.dto.PlaceAndTimeDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.repository.FlightRepository;
import com.delta.fly.service.abstraction.*;
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
    private AirlineCompanyService airlineCompanyService;

    @Autowired
    private PlaceAndTimeService placeAndTimeService;

    @Autowired
    private TicketService ticketService;

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAllByDeletedIsFalse();
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
            company = Optional.ofNullable(airlineCompanyService.getOne(dto.getAirlineCompanyID()));
            if (!company.isPresent()) {
                throw new ObjectNotFoundException("Airline company with ID: " + dto.getAirlineCompanyID() + " not found!");
            }
            airplane = Optional.ofNullable(airplaneService.getOne(dto.getAirplaneID()));
            if (!airplane.isPresent()) {
                throw new ObjectNotFoundException("Airplane with ID: " + dto.getAirplaneID() + " not found!");
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
        }
    }

    @Override
    public Flight update(Flight flight) throws ObjectNotFoundException, InvalidInputException {
        Optional<Flight> uFlight;
        try {
            uFlight = Optional.ofNullable(getOne(flight.getId()));
            if (uFlight.isPresent()) {
                checks(flight.getArrival(), flight.getDeparture(), flight.getTransfers(), flight.getAirlineCompany(), flight.getAirplane());
                uFlight.get().setAirlineCompany(flight.getAirlineCompany());
                uFlight.get().setAirplane(flight.getAirplane());
                uFlight.get().setArrival(flight.getArrival());
                uFlight.get().setDeparture(flight.getDeparture());
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
            // TODO: Check if tickets were sold.
            flight.setDeleted(true);
            return flight.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Flight with ID: " + id + " not found!", ex);
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
