package com.delta.fly.service.abstraction;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> findAll();
    Passenger getOne(Long id) throws ObjectNotFoundException;
    Passenger create(RegisterDTO dto) throws InvalidInputException;
    Passenger update(Passenger passenger) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;
    //List<Passenger> filterSearch();
    
}
