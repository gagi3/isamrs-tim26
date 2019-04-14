package com.delta.fly.controller;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Passenger;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/addPassenger", method = RequestMethod.POST)
    public ResponseEntity<Passenger> create(@RequestBody RegisterDTO dto) throws InvalidInputException, ObjectNotFoundException {

        Passenger newPassenger = passengerService.create(dto);

        return new ResponseEntity<>(newPassenger, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatePassenger", method = RequestMethod.POST)
    public ResponseEntity<Passenger> update(@RequestBody Passenger passenger) throws ObjectNotFoundException {

        Passenger updatePassenger = passengerService.update(passenger);

        return new ResponseEntity<>(updatePassenger, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletePassenger/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = passengerService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
