package com.delta.fly.service.implementation;

import com.delta.fly.dto.FriendReservationDTO;
import com.delta.fly.enumeration.Class;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.repository.PriceListRepository;
import com.delta.fly.repository.TicketRepository;
import com.delta.fly.security.TokenUtils;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import com.delta.fly.service.abstraction.PassengerService;
import com.delta.fly.service.abstraction.ReservationService;
import com.delta.fly.service.abstraction.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private AirlineCompanyService airlineCompanyService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Ticket getOne(Long id) throws ObjectNotFoundException {
        return ticketRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Ticket with ID: " + id + " doesn't exist!"));
    }

    @Override
    public Ticket create(Flight flight, Seat seat) throws ObjectNotFoundException, InvalidInputException {
        Optional<Ticket> ticket;
        Optional<PriceList> priceList;
        try {
//            priceList = priceListRepository.findByAirlineCompanyId(flight.getAirlineCompany().getId());
            priceList = Optional.ofNullable(priceListRepository.findPriceListByAirlineCompany(flight.getAirlineCompany()));
            if (!flight.getAirplane().getSeats().contains(seat)) {
                throw new InvalidInputException("Chosen seat doesn't exist in this airplane!");
            }
            if (flight.getDeparture().getTheTime().before(new Date())) {
                throw new InvalidInputException("Flight has already started!");
            }
            if (!priceList.isPresent()) {
                throw new ObjectNotFoundException("Price List not defined for this company!");
            }
            ticket = Optional.ofNullable(new Ticket());
            ticket.get().setSeat(seat);
            ticket.get().setPrice(setPrice(priceList.get(), seat, flight));
            ticket.get().setFlight(flight);
            ticket.get().setDeleted(false);
            ticket.get().setConfirmed(false);
//            ticket.get().setPassenger(null);
            return ticketRepository.save(ticket.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Ticket update(Ticket ticket) throws ObjectNotFoundException {
        Optional<Ticket> uTicket;
        try {
            uTicket = Optional.ofNullable(getOne(ticket.getId()));
            if (uTicket.isPresent()) {
                uTicket.get().setDeleted(ticket.getDeleted());
                uTicket.get().setFlight(ticket.getFlight());
                uTicket.get().setPrice(ticket.getPrice());
                uTicket.get().setSeat(ticket.getSeat());
                uTicket.get().setPassenger(ticket.getPassenger());
                return ticketRepository.save(uTicket.get());
            } else {
                throw new ObjectNotFoundException("Ticket with ID: " + ticket.getId() + " doesn't exist!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Ticket with ID: " + ticket.getId() + " doesn't exist!", ex);
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Ticket ticket = getOne(id);
            ticket.setDeleted(true);
            ticketRepository.save(ticket);
            return ticket.getDeleted();
        } catch (ObjectNotFoundException ex) {
            throw new ObjectNotFoundException("Ticket with ID: " + id + " doesn't exist!");
        }
    }

    @Override
    public List<Ticket> discount(List<Ticket> tix) throws ObjectNotFoundException {
        Optional<AirlineCompany> company;
        Optional<PriceList> priceList;
        try {
            company = Optional.ofNullable(userDetailsService.getAdmin().getAirlineCompany());
            if (!company.isPresent()) {
                throw new ObjectNotFoundException("Admin doesn't moderate this company.");
            }
            priceList = Optional.ofNullable(priceListRepository.findPriceListByAirlineCompany(company.get()));
            if (!priceList.isPresent()) {
                throw new ObjectNotFoundException("Price List for this company doesn't exist.");
            }
            List<Ticket> disc = new ArrayList<>();
            for (Ticket ticket : tix) {
                Optional<Ticket> t = Optional.ofNullable(getOne(ticket.getId()));
                if (t.isPresent()) {
                    if (!ticketRepository.findAllByFlight_AirlineCompany(company.get()).contains(t.get())) {
                        throw new ObjectNotFoundException("Selected ticket(s) were not issued for your company.");
                    }
                    if (t.get().getPassenger() != null) {
                        throw new ObjectNotFoundException("Ticket already purchased!");
                    }
                    if (company.get().getDiscountedTickets().contains(t.get())) {
                        throw new ObjectNotFoundException("Ticket already discounted!");
                    } else {
                        t.get().setPrice(ticket.getPrice() * (100 - priceList.get().getDiscountPercentage()) / 100);
                        update(t.get());
                        disc.add(t.get());
                    }
                }
            }
            if (company.get().getDiscountedTickets() != null) {
                company.get().getDiscountedTickets().addAll(disc);
            } else {
                company.get().setDiscountedTickets(disc);
            }
            airlineCompanyService.update(company.get());
            return company.get().getDiscountedTickets();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public List<Ticket> getDiscounted() throws ObjectNotFoundException {
        if (userDetailsService.getUsername().equals("anonymousUser")) {
            List<Ticket> discounted = new ArrayList<>();
            for (AirlineCompany airlineCompany : airlineCompanyService.findAll()) {
                discounted.addAll(airlineCompany.getDiscountedTickets());
            }
            return discounted;
        }
        Optional<AirlineCompanyAdmin> admin = Optional.empty();
        try {
            admin = Optional.ofNullable(userDetailsService.getAdmin());
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            Optional<AirlineCompany> company = Optional.empty();
            if (admin.isPresent()) {
                company = Optional.ofNullable(admin.get().getAirlineCompany());
            }
            if (company.isPresent()) {
                return company.get().getDiscountedTickets();
            } else {
                List<Ticket> discounted = new ArrayList<>();
                for (AirlineCompany airlineCompany : airlineCompanyService.findAll()) {
                    for (Ticket t : airlineCompany.getDiscountedTickets()) {
                        if (!t.getDeleted() && t.getPassenger() == null) {
                            discounted.add(t);
                        }
                    }
                }
                return discounted;
            }
        }
    }

    private Double setPrice(PriceList priceList, Seat seat, Flight flight) {
        Double price = priceList.getPriceByKm();
        Class seatClass = seat.getSeatClass();
        if (seatClass.equals(Class.BUSINESS)) {
            price *= priceList.getBusinessClassPriceCoefficient();
        } else if (seatClass.equals(Class.ECONOMY)) {
            price *= priceList.getEconomyClassPriceCoefficient();
        } else if (seatClass.equals(Class.FIRST)) {
            price *= priceList.getFirstClassPriceCoefficient();
        }
        return price * flight.getDistance();
    }

    @Override
    public Ticket reserve(Ticket ticket, Boolean quick, Integer luggage) throws ObjectNotFoundException {
        Optional<Ticket> quickTicket = Optional.empty();
        Optional<Passenger> passenger;
        Optional<PriceList> priceList;
        try {
            if (quick) {
                for (AirlineCompany company : airlineCompanyService.findAll()) {
                    for (Ticket t : company.getDiscountedTickets()) {
                        if (t.getId().equals(ticket.getId())) {
                            quickTicket = Optional.of(ticketRepository.getOne(ticket.getId()));
                        }
                    }
                }
                if (!quickTicket.isPresent()) {
                    throw new ObjectNotFoundException("Ticket not previously discounted!");
                }
            } else {
                quickTicket = Optional.of(ticketRepository.getOne(ticket.getId()));
                for (AirlineCompany company : airlineCompanyService.findAll()) {
                    if (company.getDiscountedTickets().contains(quickTicket.get())) {
                        throw new ObjectNotFoundException("Ticket is discounted, therefore unavailable for reservation.");
                    }
                }
            }
            if (quickTicket.get().getPassenger() != null) {
                throw new ObjectNotFoundException("Ticket already reserved.");
            }
            passenger = Optional.ofNullable(userDetailsService.getPassenger());
            if (!passenger.isPresent()) {
                throw new ObjectNotFoundException("No passenger.");
            }
            for (Ticket t : quickTicket.get().getFlight().getTickets()) {
                if (t.getPassenger() != null) {
                    if (t.getPassenger().equals(passenger.get())) {
                        throw new ObjectNotFoundException("You cannot reserve more than one ticket for a flight!");
                    }
                }
            }
            priceList = priceListRepository.findByAirlineCompany(quickTicket.get().getFlight().getAirlineCompany());
            if (!priceList.isPresent()) {
                throw new ObjectNotFoundException("Price List doesn't exist for this company.");
            }
            quickTicket.get().setPrice(quickTicket.get().getPrice() + (luggage * priceList.get().getPriceByLuggageItem()));
            quickTicket.get().setConfirmed(true);
            Email email = new Email();
            email.setMessage(emailService.reservationTemplate(passenger.get().getFirstName(), quickTicket.get()));
            email.setSubject("Ticket reservation");
            email.setTo(passenger.get().getUsername());
            emailService.send(email);
            quickTicket.get().setPassenger(passenger.get());
            if (passenger.get().getTickets() == null) {
                Optional<Ticket> finalQuickTicket = quickTicket;
                passenger.get().setTickets(new ArrayList<Ticket>() {{ add(finalQuickTicket.get()); }} );
            } else {
                passenger.get().getTickets().add(quickTicket.get());
            }
            quickTicket.get().getFlight().getAirlineCompany().getDiscountedTickets().remove(quickTicket.get());
            if (quickTicket.get().getConfirmed()) {
                reservationService.create(quickTicket.get(), passenger.get());
            }
            airlineCompanyService.update(quickTicket.get().getFlight().getAirlineCompany());
            passengerService.update(passenger.get());
            return update(quickTicket.get());
        } catch (ObjectNotFoundException | InvalidInputException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Ticket friendReserve(FriendReservationDTO dto, Integer luggage) throws ObjectNotFoundException {
        Optional<Ticket> quickTicket;
        Optional<Passenger> passenger;
        Optional<Passenger> you;
        Optional<PriceList> priceList;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            if (!you.isPresent()) {
                throw new ObjectNotFoundException("Unauthorized.");
            }
            quickTicket = Optional.of(ticketRepository.getOne(dto.getTicket().getId()));
            for (AirlineCompany company : airlineCompanyService.findAll()) {
                if (company.getDiscountedTickets().contains(quickTicket.get())) {
                    throw new ObjectNotFoundException("Ticket is discounted, therefore unavailable for reservation.");
                }
            }
            if (quickTicket.get().getPassenger() != null) {
                throw new ObjectNotFoundException("Ticket already reserved.");
            }
            passenger = Optional.ofNullable(passengerService.getOne(dto.getPassenger().getId()));
            if (!passenger.isPresent()) {
                throw new ObjectNotFoundException("No passenger.");
            }
            if (!you.get().getFriends().contains(passenger.get()) || !passenger.get().getFriends().contains(you.get())) {
                throw new ObjectNotFoundException("You are not friends!");
            }
            for (Ticket t : quickTicket.get().getFlight().getTickets()) {
                if (t.getPassenger().equals(passenger.get())) {
                    throw new ObjectNotFoundException("You cannot reserve more than one ticket for a flight!");
                }
            }
            priceList = priceListRepository.findByAirlineCompany(quickTicket.get().getFlight().getAirlineCompany());
            if (!priceList.isPresent()) {
                throw new ObjectNotFoundException("Price List doesn't exist for this company.");
            }
            quickTicket.get().setPrice(quickTicket.get().getPrice() + (luggage * priceList.get().getPriceByLuggageItem()));
            Email email = new Email();
            email.setMessage(emailService.friendReservationTemplate(passenger.get().getFirstName(), quickTicket.get()));
            email.setSubject("Confirm ticket reservation");
            email.setTo(passenger.get().getUsername());
            emailService.send(email);
            quickTicket.get().setPassenger(passenger.get());
            if (passenger.get().getTickets() == null) {
                Optional<Ticket> finalQuickTicket = quickTicket;
                passenger.get().setTickets(new ArrayList<Ticket>() {{ add(finalQuickTicket.get()); }} );
            } else {
                passenger.get().getTickets().add(quickTicket.get());
            }
            quickTicket.get().getFlight().getAirlineCompany().getDiscountedTickets().remove(quickTicket.get());
            if (quickTicket.get().getConfirmed()) {
                reservationService.create(quickTicket.get(), passenger.get());
            }
            airlineCompanyService.update(quickTicket.get().getFlight().getAirlineCompany());
            passengerService.update(passenger.get());
            return update(quickTicket.get());
        } catch (ObjectNotFoundException | InvalidInputException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Boolean confirm(Long ticketID) throws ObjectNotFoundException {
        Optional<Ticket> ticket;
        Optional<Passenger> passenger;
        try {
            ticket = ticketRepository.findById(ticketID);
            if (!ticket.isPresent()) {
                throw new ObjectNotFoundException("Ticket not found!");
            }
            passenger = Optional.ofNullable(userDetailsService.getPassenger());
            if (!passenger.isPresent()) {
                throw new ObjectNotFoundException("Passenger not found!");
            }
            if (!passenger.get().equals(ticket.get().getPassenger())) {
                throw new ObjectNotFoundException("This is not your ticket.");
            }
            ticket.get().setConfirmed(true);
            update(ticket.get());
            if (ticket.get().getConfirmed()) {
                reservationService.create(ticket.get(), passenger.get());
            }
            return ticket.get().getConfirmed();

        } catch (ObjectNotFoundException | InvalidInputException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Boolean cancel(Ticket ticket) throws ObjectNotFoundException {
        Optional<Ticket> theTicket;
        Optional<Passenger> passenger;
        Optional<Reservation> reservation;
        try {
            theTicket = Optional.ofNullable(getOne(ticket.getId()));
            if (!theTicket.isPresent()) {
                throw new ObjectNotFoundException("Ticket not found!");
            }
            passenger = Optional.ofNullable(userDetailsService.getPassenger());
            if (!passenger.isPresent()) {
                throw new ObjectNotFoundException("Passenger not found!");
            }
            if (!passenger.get().equals(theTicket.get().getPassenger())) {
                throw new ObjectNotFoundException("This is not your ticket.");
            }
            reservation = Optional.ofNullable(reservationService.getByTicket(theTicket.get()));
            if (!reservation.isPresent()) {
                throw new ObjectNotFoundException("Reservation not found!");
            }
            if (!reservation.get().getTicket().equals(theTicket.get()) || !reservation.get().getPassenger().equals(passenger.get())) {
                throw new ObjectNotFoundException("Invalid data.");
            }
            reservation.get().setPassenger(null);
            reservation.get().setDeleted(true);
            theTicket.get().setConfirmed(false);
            theTicket.get().setPassenger(null);
            update(theTicket.get());
            reservationService.update(reservation.get());
            if (theTicket.get().getConfirmed()) {
                reservationService.create(theTicket.get(), passenger.get());
            }
            return reservation.get().getDeleted();

        } catch (ObjectNotFoundException | InvalidInputException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

}
