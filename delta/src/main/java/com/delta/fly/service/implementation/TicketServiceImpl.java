package com.delta.fly.service.implementation;

import com.delta.fly.enumeration.Class;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;
import com.delta.fly.model.PriceList;
import com.delta.fly.model.Seat;
import com.delta.fly.model.Ticket;
import com.delta.fly.repository.PriceListRepository;
import com.delta.fly.repository.TicketRepository;
import com.delta.fly.service.abstraction.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PriceListRepository priceListRepository;

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
            priceList = Optional.ofNullable(priceListRepository.findByAirlineCompany(flight.getAirlineCompany()));
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
            ticket.get().setPrice(setPrice(priceList.get(), seat));
            ticket.get().setFlight(flight);
            ticket.get().setDeleted(false);
            ticket.get().setPassenger(null);
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
            return ticket.getDeleted();
        } catch (ObjectNotFoundException ex) {
            throw new ObjectNotFoundException("Ticket with ID: " + id + " doesn't exist!");
        }
    }

    private Double setPrice(PriceList priceList, Seat seat) {
        Double price = priceList.getPriceByKm();
        Class seatClass = seat.getSeatClass();
        if (seatClass.equals(Class.BUSINESS)) {
            price *= priceList.getBusinessClassPriceCoefficient();
        } else if (seatClass.equals(Class.ECONOMY)) {
            price *= priceList.getEconomyClassPriceCoefficient();
        } else if (seatClass.equals(Class.FIRST)) {
            price *= priceList.getFirstClassPriceCoefficient();
        }
        return price;
    }
}
