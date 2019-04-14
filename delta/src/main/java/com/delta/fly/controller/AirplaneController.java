package com.delta.fly.controller;

import com.delta.fly.dto.AirplaneDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Airplane;
import com.delta.fly.service.abstraction.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/airplane")
public class AirplaneController {

    @Autowired
    AirplaneService airplaneService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Airplane>> getAll() {

        List<Airplane> airplanes = airplaneService.findAll();

        return new ResponseEntity<>(airplanes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Airplane> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        Airplane airplane = airplaneService.getOne(id);

        return new ResponseEntity<>(airplane, HttpStatus.OK);
    }

    @RequestMapping(value = "/addAirplane", method = RequestMethod.POST)
    public ResponseEntity<Airplane> create(@RequestBody AirplaneDTO dto) throws InvalidInputException, ObjectNotFoundException {

        Airplane newAirplane = airplaneService.create(dto);

        return new ResponseEntity<>(newAirplane, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateAirplane", method = RequestMethod.POST)
    public ResponseEntity<Airplane> update(@RequestBody Airplane airplane) throws ObjectNotFoundException {

        Airplane updateAirplane = airplaneService.update(airplane);

        return new ResponseEntity<>(updateAirplane, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteAirplane/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = airplaneService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}