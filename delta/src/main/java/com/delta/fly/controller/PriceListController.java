package com.delta.fly.controller;

import com.delta.fly.dto.PriceListDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.PriceList;
import com.delta.fly.service.abstraction.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/price-list")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<PriceList>> getAll() {

        List<PriceList> priceLists = priceListService.findAll();

        return new ResponseEntity<>(priceLists, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PriceList> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        PriceList priceList = priceListService.getOne(id);

        return new ResponseEntity<>(priceList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<PriceList> create(@RequestBody PriceListDTO dto) throws InvalidInputException, ObjectNotFoundException {

        PriceList newPriceList = priceListService.create(dto);

        return new ResponseEntity<>(newPriceList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<PriceList> update(@RequestBody PriceList priceList) throws ObjectNotFoundException, InvalidInputException {

        PriceList updatePriceList = priceListService.update(priceList);

        return new ResponseEntity<>(updatePriceList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AIRLINECOMPANYADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = priceListService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
    
}
