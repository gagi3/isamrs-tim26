package com.delta.fly.service.implementation;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.enumeration.RoleName;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Role;
import com.delta.fly.model.SystemAdmin;
import com.delta.fly.repository.SystemAdminRepository;
import com.delta.fly.service.abstraction.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {

    @Autowired
    SystemAdminRepository systemAdminRepository;

    @Override
    public List<SystemAdmin> findAll() {
        return systemAdminRepository.findAllByDeletedIsFalse();
    }

    @Override
    public SystemAdmin getOne(Long id) throws ObjectNotFoundException {
        return systemAdminRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Admin with ID: " + id + " not found!"));
    }

    @Override
    public SystemAdmin create(RegisterDTO dto) throws InvalidInputException {
        Optional<SystemAdmin> admin;
        try {
            admin = systemAdminRepository.findByUsername(dto.getUsername());
            if (!admin.isPresent()) {
                admin = Optional.of(new SystemAdmin());
                admin.get().setUsername(dto.getUsername());
                admin.get().setPassword(dto.getPassword());
                admin.get().setFirstName(dto.getFirstName());
                admin.get().setLastName(dto.getLastName());
                admin.get().setPhoneNumber(dto.getPhoneNumber());
                admin.get().setCity(dto.getCity());
                admin.get().setRoles(new ArrayList<Role>() {{ add(new Role(RoleName.ROLE_SYSTEMADMIN)); }} );
                admin.get().setDeleted(false);
            } else {
                throw new InvalidInputException("Bad email address.");
            }
            return systemAdminRepository.save(admin.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Admin with email: " + dto.getUsername() + " already exists!", ex);
        }
    }

    @Override
    public SystemAdmin update(SystemAdmin admin) throws ObjectNotFoundException {
        Optional<SystemAdmin> uAdmin;
        try {
            uAdmin = Optional.ofNullable(getOne(admin.getId()));
            if (uAdmin.isPresent() && !admin.getDeleted()) {
                uAdmin.get().setDeleted(admin.getDeleted());
                uAdmin.get().setCity(admin.getCity());
                uAdmin.get().setFirstName(admin.getFirstName());
                uAdmin.get().setLastName(admin.getLastName());
                uAdmin.get().setPassword(admin.getPassword());
                uAdmin.get().setPhoneNumber(admin.getPhoneNumber());
                uAdmin.get().setRoles(admin.getRoles());
                uAdmin.get().setUsername(admin.getUsername());
            } else {
                throw new ObjectNotFoundException("Bad ID");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Admin with ID: " + admin.getId() + " not found!", ex);
        }
        return systemAdminRepository.save(uAdmin.get());
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            SystemAdmin admin = getOne(id);
            admin.setDeleted(true);
            return admin.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Admin with ID: " + id + " not found!", ex);
        }
    }

}
