package com.delta.fly.service.abstraction;

import com.delta.fly.dto.FriendReservationDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;
import com.delta.fly.model.Seat;
import com.delta.fly.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket getOne(Long id) throws ObjectNotFoundException;

    Ticket create(Flight flight, Seat seat) throws ObjectNotFoundException, InvalidInputException;

    Ticket update(Ticket ticket) throws ObjectNotFoundException;

    boolean delete(Long id) throws ObjectNotFoundException;

    List<Ticket> discount(List<Ticket> tix) throws ObjectNotFoundException;

    List<Ticket> getDiscounted() throws ObjectNotFoundException;

    Ticket reserve(Ticket ticket, Boolean quick, Integer luggage) throws ObjectNotFoundException;

    List<Ticket> generate(Long id) throws ObjectNotFoundException, InvalidInputException;

    Ticket friendReserve(FriendReservationDTO dto, Integer luggage) throws ObjectNotFoundException;

    Boolean confirm(Long ticketID) throws ObjectNotFoundException;

    Boolean cancel(Ticket ticket) throws ObjectNotFoundException;
    //List<Ticket> filterSearch();

}
