package com.delta.fly.controller;

import com.delta.fly.dto.BusinessReportDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.service.abstraction.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Reservation>> getAll() {

        List<Reservation> reservations = reservationService.findAll();

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reservation> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Reservation reservation = reservationService.getOne(id);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Reservation> create(@RequestBody Ticket ticket, Passenger passenger) throws InvalidInputException, ObjectNotFoundException {

        Reservation newReservation = reservationService.create(ticket, passenger);

        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Reservation> update(@RequestBody Reservation reservation) throws ObjectNotFoundException {

        Reservation updateReservation = reservationService.update(reservation);

        return new ResponseEntity<>(updateReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = reservationService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> businessReport(@RequestBody BusinessReportDTO dto) throws ObjectNotFoundException {

        List<Reservation> reservations = reservationService.businessReport(dto);

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/regenerate", method = RequestMethod.POST)
    public ResponseEntity<List<Reservation>> regenerate() throws InvalidInputException {
        List<Reservation> reservations = reservationService.regenerate();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/get/flight/{ID}", method = RequestMethod.GET)
    public ResponseEntity<Flight> getFlight(@PathVariable Long ID) throws ObjectNotFoundException {
        Flight flight = reservationService.getFlight(ID);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/get/ticket", method = RequestMethod.POST)
    public ResponseEntity<Reservation> getByTicket(@RequestBody Ticket ticket) throws ObjectNotFoundException {
        Reservation reservation = reservationService.getByTicket(ticket);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
    
}
