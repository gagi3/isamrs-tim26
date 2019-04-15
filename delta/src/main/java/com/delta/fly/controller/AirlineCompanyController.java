package com.delta.fly.controller;

import com.delta.fly.dto.AirlineCompanyRegistrationDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompany;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/airline-company")
public class AirlineCompanyController {

    @Autowired
    private AirlineCompanyService airlineCompanyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<AirlineCompany>> getAll() {

        List<AirlineCompany> companies = airlineCompanyService.findAll();

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AirlineCompany> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        AirlineCompany company = airlineCompanyService.getOne(id);

        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/addAirlineCompany", method = RequestMethod.POST)
    public ResponseEntity<AirlineCompany> create(@RequestBody AirlineCompanyRegistrationDTO dto) throws InvalidInputException, ObjectNotFoundException {

        AirlineCompany newAirlineCompany = airlineCompanyService.create(dto);

        return new ResponseEntity<>(newAirlineCompany, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/updateAirlineCompany", method = RequestMethod.POST)
    public ResponseEntity<AirlineCompany> update(@RequestBody AirlineCompany company) throws ObjectNotFoundException {

        AirlineCompany updateAirlineCompany = airlineCompanyService.update(company);

        return new ResponseEntity<>(updateAirlineCompany, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/deleteAirlineCompany/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = airlineCompanyService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
