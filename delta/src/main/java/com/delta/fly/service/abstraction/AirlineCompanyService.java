package com.delta.fly.service.abstraction;

import com.delta.fly.dto.AirlineCompanyRegistrationDTO;
import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompany;

import java.util.List;

public interface AirlineCompanyService {

    List<AirlineCompany> findAll();
    AirlineCompany getOne(Long id) throws ObjectNotFoundException;
    AirlineCompany create(AirlineCompanyRegistrationDTO dto) throws ObjectNotFoundException, InvalidInputException;
    AirlineCompany update(AirlineCompany company) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;
    //List<AirlineCompany> filterSearch();

}
