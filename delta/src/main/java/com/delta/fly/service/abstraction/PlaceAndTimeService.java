package com.delta.fly.service.abstraction;

import com.delta.fly.dto.PlaceAndTimeDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.PlaceAndTime;

import java.util.List;

public interface PlaceAndTimeService {

    List<PlaceAndTime> findAll();

    PlaceAndTime getOne(Long id) throws ObjectNotFoundException;

    PlaceAndTime create(PlaceAndTimeDTO dto) throws InvalidInputException;

    PlaceAndTime update(PlaceAndTime placeAndTime) throws ObjectNotFoundException, InvalidInputException;

    boolean delete(Long id) throws ObjectNotFoundException;
    //List<PlaceAndTime> filterSearch();

}
