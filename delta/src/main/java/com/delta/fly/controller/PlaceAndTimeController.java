package com.delta.fly.controller;

import com.delta.fly.dto.PlaceAndTimeDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.PlaceAndTime;
import com.delta.fly.service.abstraction.PlaceAndTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/place-and-time")
public class PlaceAndTimeController {

    @Autowired
    private PlaceAndTimeService placeAndTimeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<PlaceAndTime>> getAll() {

        List<PlaceAndTime> placeAndTimes = placeAndTimeService.findAll();

        return new ResponseEntity<>(placeAndTimes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlaceAndTime> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        PlaceAndTime placeAndTime = placeAndTimeService.getOne(id);

        return new ResponseEntity<>(placeAndTime, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<PlaceAndTime> create(@RequestBody PlaceAndTimeDTO dto) throws InvalidInputException, ObjectNotFoundException {

        PlaceAndTime newPlaceAndTime = placeAndTimeService.create(dto);

        return new ResponseEntity<>(newPlaceAndTime, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<PlaceAndTime> update(@RequestBody PlaceAndTime placeAndTime) throws ObjectNotFoundException, InvalidInputException {

        PlaceAndTime updatePlaceAndTime = placeAndTimeService.update(placeAndTime);

        return new ResponseEntity<>(updatePlaceAndTime, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = placeAndTimeService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
