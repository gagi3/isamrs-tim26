package com.delta.fly.controller;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Passenger;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Passenger>> getAll() {

        List<Passenger> passengers = passengerService.findAll();

        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Passenger> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Passenger passenger = passengerService.getOne(id);

        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{username:.+}")
    public ResponseEntity<Passenger> getOneByUsername(@PathVariable String username) throws ObjectNotFoundException {

        System.out.println("Username: " + username);
        Passenger passenger = passengerService.getByUsername(username);
        if (passenger == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Passenger> create(@RequestBody RegisterDTO dto) throws InvalidInputException, ObjectNotFoundException {

        Passenger newPassenger = passengerService.create(dto);

        return new ResponseEntity<>(newPassenger, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Passenger> update(@RequestBody Passenger passenger) throws ObjectNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        Passenger updatePassenger = passengerService.update(passenger);

        return new ResponseEntity<>(updatePassenger, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = passengerService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
