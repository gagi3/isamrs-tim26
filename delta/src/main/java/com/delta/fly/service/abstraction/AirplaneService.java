package com.delta.fly.service.abstraction;

import com.delta.fly.dto.AirplaneDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Airplane;

import java.util.List;

public interface AirplaneService {

    List<Airplane> findAll();
    Airplane getOne(Long id) throws ObjectNotFoundException;
    Airplane create(AirplaneDTO dto) throws ObjectNotFoundException, InvalidInputException;
    Airplane update(Airplane airplane) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;
    //List<Airplane> filterSearch();
    
}
