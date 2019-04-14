package com.delta.fly.controller;

import com.delta.fly.dto.FlightDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;
import com.delta.fly.service.abstraction.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/flight")
public class FlightController {

    @Autowired
    FlightService flightService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Flight>> getAll() {

        List<Flight> flights = flightService.findAll();

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Flight> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Flight flight = flightService.getOne(id);

        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @RequestMapping(value = "/addFlight", method = RequestMethod.POST)
    public ResponseEntity<Flight> create(@RequestBody FlightDTO dto) throws InvalidInputException, ObjectNotFoundException {

        Flight newFlight = flightService.create(dto);

        return new ResponseEntity<>(newFlight, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateFlight", method = RequestMethod.POST)
    public ResponseEntity<Flight> update(@RequestBody Flight flight) throws ObjectNotFoundException, InvalidInputException {

        Flight updateFlight = flightService.update(flight);

        return new ResponseEntity<>(updateFlight, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFlight/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = flightService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
