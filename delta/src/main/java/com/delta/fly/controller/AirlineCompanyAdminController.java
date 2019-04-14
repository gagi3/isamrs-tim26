package com.delta.fly.controller;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.service.abstraction.AirlineCompanyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/airline-company-admin")
public class AirlineCompanyAdminController {

    @Autowired
    private AirlineCompanyAdminService airlineCompanyAdminService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<AirlineCompanyAdmin>> getAll() {

        List<AirlineCompanyAdmin> admins = airlineCompanyAdminService.findAll();

        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AirlineCompanyAdmin> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        AirlineCompanyAdmin admin = airlineCompanyAdminService.getOne(id);

        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @RequestMapping(value = "/addAirlineCompanyAdmin/{companyID}", method = RequestMethod.POST)
    public ResponseEntity<AirlineCompanyAdmin> create(@RequestBody RegisterDTO admin, @PathVariable Long companyID) throws InvalidInputException, ObjectNotFoundException {

        AirlineCompanyAdmin newAirlineCompanyAdmin = airlineCompanyAdminService.create(admin, companyID);

        return new ResponseEntity<>(newAirlineCompanyAdmin, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateAirlineCompanyAdmin", method = RequestMethod.POST)
    public ResponseEntity<AirlineCompanyAdmin> update(@RequestBody AirlineCompanyAdmin admin) throws ObjectNotFoundException {

        AirlineCompanyAdmin updateAirlineCompanyAdmin = airlineCompanyAdminService.update(admin);

        return new ResponseEntity<>(updateAirlineCompanyAdmin, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteAirlineCompanyAdmin/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = airlineCompanyAdminService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
