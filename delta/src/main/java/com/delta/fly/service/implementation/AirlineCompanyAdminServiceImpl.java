package com.delta.fly.service.implementation;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.enumeration.RoleName;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.model.Role;
import com.delta.fly.repository.AirlineCompanyAdminRepository;
import com.delta.fly.service.abstraction.AirlineCompanyAdminService;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineCompanyAdminServiceImpl implements AirlineCompanyAdminService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AirlineCompanyAdminRepository airlineCompanyAdminRepository;

    @Autowired
    private AirlineCompanyService airlineCompanyService;

    @Override
    public List<AirlineCompanyAdmin> findAll() {
        return airlineCompanyAdminRepository.findAllByDeletedIsFalse();
    }

    @Override
    public AirlineCompanyAdmin getOne(Long id) throws ObjectNotFoundException {
        return airlineCompanyAdminRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Admin with ID: " + id + " not found!"));
    }

    @Override
    public AirlineCompanyAdmin create(RegisterDTO dto, Long airlineCompanyID) throws ObjectNotFoundException, InvalidInputException {
        Optional<AirlineCompanyAdmin> admin;
        Optional<AirlineCompany> company;
        try {
            admin = airlineCompanyAdminRepository.findByUsername(dto.getUsername());
            company = Optional.ofNullable(airlineCompanyService.getOne(airlineCompanyID));
            if (!admin.isPresent()) {
                admin = Optional.of(new AirlineCompanyAdmin());
                admin.get().setUsername(dto.getUsername());
                admin.get().setPassword(encoder.encode(dto.getPassword()));
                admin.get().setFirstName(dto.getFirstName());
                admin.get().setLastName(dto.getLastName());
                admin.get().setPhoneNumber(dto.getPhoneNumber());
                admin.get().setCity(dto.getCity());
                admin.get().setRoles(new ArrayList<Role>() {{ add(new Role(RoleName.ROLE_AIRLINECOMPANYADMIN)); }} );
                admin.get().setActivated(false);
                admin.get().setDeleted(false);
                if (company.isPresent()) {
                    admin.get().setAirlineCompany(company.get());
                } else if (airlineCompanyID == 0L) {
                    admin.get().setAirlineCompany(null);
                } else {
                    throw new ObjectNotFoundException("Airline company doesn't exist.");
                }
            } else {
                throw new InvalidInputException("Bad email address.");
            }
            return airlineCompanyAdminRepository.save(admin.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Admin with email: " + dto.getUsername() + " already exists!", ex);
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Airline company with ID: " + airlineCompanyID + " not found!", ex);
        }
    }

    @Override
    public AirlineCompanyAdmin update(AirlineCompanyAdmin admin) throws ObjectNotFoundException {
        Optional<AirlineCompanyAdmin> uAdmin;
        try {
            uAdmin = Optional.ofNullable(getOne(admin.getId()));
            if (uAdmin.isPresent() && !admin.getDeleted()) {
                uAdmin.get().setDeleted(admin.getDeleted());
                uAdmin.get().setAirlineCompany(admin.getAirlineCompany());
                uAdmin.get().setCity(admin.getCity());
                uAdmin.get().setFirstName(admin.getFirstName());
                uAdmin.get().setLastName(admin.getLastName());
                uAdmin.get().setPassword(admin.getPassword());
                uAdmin.get().setPhoneNumber(admin.getPhoneNumber());
                uAdmin.get().setRoles(admin.getRoles());
                uAdmin.get().setActivated(admin.getActivated());
                uAdmin.get().setUsername(admin.getUsername());
            } else {
                throw new ObjectNotFoundException("Bad ID");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Admin with ID: " + admin.getId() + " not found!", ex);
        }
        return airlineCompanyAdminRepository.save(uAdmin.get());
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            AirlineCompanyAdmin admin = getOne(id);
            admin.setDeleted(true);
            return admin.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Admin with ID: " + id + " not found!", ex);
        }
    }
}
