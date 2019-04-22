package com.delta.fly.service.abstraction;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.SystemAdmin;

import java.util.List;

public interface SystemAdminService {

    List<SystemAdmin> findAll();
    SystemAdmin getOne(Long id) throws ObjectNotFoundException;

    SystemAdmin getByUsername(String username) throws ObjectNotFoundException;

    SystemAdmin create(RegisterDTO dto) throws InvalidInputException;
    SystemAdmin update(SystemAdmin admin) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;
    //List<SystemAdmin> filterSearch();

}
