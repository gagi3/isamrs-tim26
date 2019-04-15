package com.delta.fly.controller;

import com.delta.fly.dto.SeatDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Seat;
import com.delta.fly.service.abstraction.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Seat>> getAll() {

        List<Seat> seats = seatService.findAll();

        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Seat> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Seat seat = seatService.getOne(id);

        return new ResponseEntity<>(seat, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/addSeat", method = RequestMethod.POST)
    public ResponseEntity<Seat> create(@RequestBody SeatDTO dto) throws InvalidInputException, ObjectNotFoundException {

        Seat newSeat = seatService.create(dto);

        return new ResponseEntity<>(newSeat, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/updateSeat", method = RequestMethod.POST)
    public ResponseEntity<Seat> update(@RequestBody Seat seat) throws ObjectNotFoundException {

        Seat updateSeat = seatService.update(seat);

        return new ResponseEntity<>(updateSeat, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/deleteSeat/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = seatService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
