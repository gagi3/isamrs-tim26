package com.delta.fly.service.implementation;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.enumeration.RoleName;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.model.Role;
import com.delta.fly.repository.AirlineCompanyAdminRepository;
import com.delta.fly.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<AirlineCompanyAdmin> findAll() {
        return airlineCompanyAdminRepository.findAllByDeletedIsFalse();
    }

    @Override
    public AirlineCompanyAdmin getOne(Long id) throws ObjectNotFoundException {
        return airlineCompanyAdminRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Admin with ID: " + id + " not found!"));
    }

    @Override
    public AirlineCompanyAdmin getByUsername(String username) throws ObjectNotFoundException {
        return airlineCompanyAdminRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Admin with email: " + username + " not found!"));
    }

    @Override
    public AirlineCompanyAdmin create(RegisterDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<AirlineCompanyAdmin> admin;
        try {
            admin = airlineCompanyAdminRepository.findByUsername(dto.getUsername());
            Boolean a = airlineCompanyAdminRepository.existsAirlineCompanyAdminByPhoneNumber(dto.getPhoneNumber());
            if (a) {
                throw new InvalidInputException("Admin with phone number: " + dto.getPhoneNumber() + " already exists!");
            }
            if (!admin.isPresent()) {
                admin = Optional.of(new AirlineCompanyAdmin());
                admin.get().setUsername(dto.getUsername());
                admin.get().setPassword(encoder.encode(dto.getPassword()));
                admin.get().setFirstName(dto.getFirstName());
                admin.get().setLastName(dto.getLastName());
                admin.get().setPhoneNumber(dto.getPhoneNumber());
                admin.get().setCity(dto.getCity());
                Optional<Role> role = roleRepository.findByRoleName(RoleName.ROLE_AIRLINECOMPANYADMIN);
                if (role.isPresent()) {
                    admin.get().setRoles(new ArrayList<Role>() {{ add(role.get()); }});
                } else {
                    admin.get().setRoles(new ArrayList<Role>() {{
                        add(new Role(RoleName.ROLE_AIRLINECOMPANYADMIN));
                    }});
                }
                admin.get().setActivated(false);
                admin.get().setDeleted(false);
            } else {
                throw new InvalidInputException("Admin with email: " + dto.getUsername() + " already exists!");
            }
            return airlineCompanyAdminRepository.save(admin.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Invalid input!", ex);
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
                uAdmin.get().setPassword(encoder.encode(admin.getPassword()));
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
