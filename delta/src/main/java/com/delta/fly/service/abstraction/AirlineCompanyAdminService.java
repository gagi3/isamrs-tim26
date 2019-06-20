package com.delta.fly.service.abstraction;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompanyAdmin;

import java.util.List;

public interface AirlineCompanyAdminService {

    List<AirlineCompanyAdmin> findAll();

    AirlineCompanyAdmin getOne(Long id) throws ObjectNotFoundException;

    AirlineCompanyAdmin getByUsername(String username) throws ObjectNotFoundException;

    AirlineCompanyAdmin create(RegisterDTO dto) throws ObjectNotFoundException, InvalidInputException;

    AirlineCompanyAdmin update(AirlineCompanyAdmin admin) throws ObjectNotFoundException;

    boolean delete(Long id) throws ObjectNotFoundException;
    //List<AirlineCompanyAdmin> filterSearch();

}
