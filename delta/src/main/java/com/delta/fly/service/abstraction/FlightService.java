package com.delta.fly.service.abstraction;

import com.delta.fly.dto.FlightDTO;
import com.delta.fly.dto.FlightSearchFilterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> findAll();
    Flight getOne(Long id) throws ObjectNotFoundException;
    Flight create(FlightDTO dto) throws ObjectNotFoundException, InvalidInputException;
    Flight update(Flight flight) throws ObjectNotFoundException, InvalidInputException;
    boolean delete (Long id) throws ObjectNotFoundException;

    List<Flight> filterSearch(FlightSearchFilterDTO dto);
    //List<Flight> filterSearch();
    
}
