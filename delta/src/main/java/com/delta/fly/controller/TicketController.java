package com.delta.fly.controller;

import com.delta.fly.dto.DiscountTicketsDTO;
import com.delta.fly.dto.FriendReservationDTO;
import com.delta.fly.dto.ReservationDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;
import com.delta.fly.model.Seat;
import com.delta.fly.model.Ticket;
import com.delta.fly.service.abstraction.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getAll() {

        List<Ticket> tickets = ticketService.findAll();

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Ticket ticket = ticketService.getOne(id);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Ticket> create(@RequestBody Flight flight, @RequestBody Seat seat) throws InvalidInputException, ObjectNotFoundException {

        Ticket newTicket = ticketService.create(flight, seat);

        return new ResponseEntity<>(newTicket, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/generate/{id}", method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> generate(@PathVariable Long id) throws ObjectNotFoundException, InvalidInputException {
        List<Ticket> tickets = ticketService.generate(id);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Ticket> update(@RequestBody Ticket ticket) throws ObjectNotFoundException {

        Ticket updateTicket = ticketService.update(ticket);

        return new ResponseEntity<>(updateTicket, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = ticketService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/discount", method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> discount(@RequestBody DiscountTicketsDTO tickets) throws ObjectNotFoundException {
        List<Ticket> tix = ticketService.discount(tickets.getTickets());
        return new ResponseEntity<>(tix, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/discounted", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getDiscounted() throws ObjectNotFoundException {
        List<Ticket> tix = ticketService.getDiscounted();
        return new ResponseEntity<>(tix, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<Ticket> reserve(@RequestBody ReservationDTO DTO) throws ObjectNotFoundException {
        Ticket reserved = ticketService.reserve(DTO.getTicket(), false, DTO.getLuggage());
        return new ResponseEntity<>(reserved, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/reserve/friend", method = RequestMethod.POST)
    public ResponseEntity<Ticket> friendReserve(@RequestBody FriendReservationDTO DTO) throws ObjectNotFoundException {
        Ticket reserved = ticketService.friendReserve(DTO, DTO.getLuggage());
        return new ResponseEntity<>(reserved, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/reserve/quick", method = RequestMethod.POST)
    public ResponseEntity<Ticket> quickReserve(@RequestBody ReservationDTO DTO) throws ObjectNotFoundException {
        Ticket reserved = ticketService.reserve(DTO.getTicket(), true, DTO.getLuggage());
        return new ResponseEntity<>(reserved, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/confirm/{ID}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> confirm(@PathVariable Long ID) throws ObjectNotFoundException {
        Boolean confirmed = ticketService.confirm(ID);
        return new ResponseEntity<>(confirmed, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<Boolean> cancel(@RequestBody Ticket ticket) throws ObjectNotFoundException {
        Boolean cancelled = ticketService.cancel(ticket);
        return new ResponseEntity<>(cancelled, HttpStatus.OK);
    }


}
