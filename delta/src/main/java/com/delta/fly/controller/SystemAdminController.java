package com.delta.fly.controller;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.SystemAdmin;
import com.delta.fly.service.abstraction.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/system-admin")
public class SystemAdminController {

    @Autowired
    private SystemAdminService systemAdminService;

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<SystemAdmin>> getAll() {

        List<SystemAdmin> systemAdmins = systemAdminService.findAll();

        return new ResponseEntity<>(systemAdmins, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SystemAdmin> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        SystemAdmin systemAdmin = systemAdminService.getOne(id);

        return new ResponseEntity<>(systemAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    public ResponseEntity<SystemAdmin> getOne(@PathVariable String username) throws ObjectNotFoundException {

        SystemAdmin systemAdmin = systemAdminService.getByUsername(username);

        return new ResponseEntity<>(systemAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SystemAdmin> create(@RequestBody RegisterDTO dto) throws InvalidInputException, ObjectNotFoundException {

        SystemAdmin newSystemAdmin = systemAdminService.create(dto);

        return new ResponseEntity<>(newSystemAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<SystemAdmin> update(@RequestBody SystemAdmin systemAdmin) throws ObjectNotFoundException {

        SystemAdmin updateSystemAdmin = systemAdminService.update(systemAdmin);

        return new ResponseEntity<>(updateSystemAdmin, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = systemAdminService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
