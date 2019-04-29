package com.delta.fly.service.implementation;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.enumeration.RoleName;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Email;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.Role;
import com.delta.fly.model.UserPrinciple;
import com.delta.fly.repository.PassengerRepository;
import com.delta.fly.repository.RoleRepository;
import com.delta.fly.security.TokenUtils;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Passenger getOne(Long id) throws ObjectNotFoundException {
        return passengerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Passenger with ID: " + id + " not found!"));
    }

    @Override
    public Passenger getByUsername(String username) throws ObjectNotFoundException {
        return passengerRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Passenger with email: " + username + " not found!"));
    }

    @Override
    public Passenger create(RegisterDTO dto) throws InvalidInputException {
        Optional<Passenger> passenger;
        try {
            passenger = passengerRepository.findByUsername(dto.getUsername());
            if (!passenger.isPresent()) {
                passenger = Optional.ofNullable(new Passenger());
                passenger.get().setUsername(dto.getUsername());
                passenger.get().setPassword(encoder.encode(dto.getPassword()));
                passenger.get().setFirstName(dto.getFirstName());
                passenger.get().setLastName(dto.getLastName());
                passenger.get().setPhoneNumber(dto.getPhoneNumber());
                passenger.get().setCity(dto.getCity());
                passenger.get().setFriends(new ArrayList<>());
                passenger.get().setTickets(new ArrayList<>());
                Optional<Role> role = roleRepository.findByRoleName(RoleName.ROLE_PASSENGER);
                if (role.isPresent()) {
                    passenger.get().setRoles(new ArrayList<Role>() {{ add(role.get()); }});
                } else {
                    passenger.get().setRoles(new ArrayList<Role>() {{
                        add(new Role(RoleName.ROLE_PASSENGER));
                    }});
                }
                passenger.get().setActivated(false);
                passenger.get().setDeleted(false);
                Email email = new Email();
                String token = tokenUtils.generateToken(UserPrinciple.build(passenger.get()));
                email.setMessage(emailService.registrationTemplate(passenger.get().getFirstName(), token));
                email.setSubject("Account activation");
                email.setTo(passenger.get().getUsername());
                emailService.send(email);
            } else {
                throw new InvalidInputException("Bad email address.");
            }
            return passengerRepository.save(passenger.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Passenger with email: " + dto.getUsername() + " already exists!", ex);
        }
    }

    @Override
    public Passenger update(Passenger passenger) throws ObjectNotFoundException {
        Optional<Passenger> uPassenger;
        try {
            uPassenger = Optional.ofNullable(getOne(passenger.getId()));
            if (uPassenger.isPresent() && !passenger.getDeleted()) {
                uPassenger.get().setDeleted(passenger.getDeleted());
                uPassenger.get().setCity(passenger.getCity());
                uPassenger.get().setFirstName(passenger.getFirstName());
                uPassenger.get().setLastName(passenger.getLastName());
                uPassenger.get().setPassword(encoder.encode(passenger.getPassword()));
                uPassenger.get().setPhoneNumber(passenger.getPhoneNumber());
                uPassenger.get().setTickets(passenger.getTickets());
                uPassenger.get().setFriends(passenger.getFriends());
                uPassenger.get().setRoles(passenger.getRoles());
                uPassenger.get().setActivated(passenger.getActivated());
                uPassenger.get().setUsername(passenger.getUsername());
            } else {
                throw new ObjectNotFoundException("Bad ID");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Passenger with ID: " + passenger.getId() + " not found!", ex);
        }
        return passengerRepository.save(uPassenger.get());
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Passenger passenger = getOne(id);
            passenger.setDeleted(true);
            return passenger.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Passenger with ID: " + id + " not found!", ex);
        }
    }

    private void remove(String email) {
        Optional<Passenger> passenger = passengerRepository.findByUsername(email);
        passenger.ifPresent(value -> passengerRepository.delete(value));
    }

    @Override
    public boolean activate(String token) throws InvalidInputException, ObjectNotFoundException {
        String username = tokenUtils.getUsernameFromToken(token);
        Optional<Passenger> passenger = passengerRepository.findByUsername(username);
        if (passenger.isPresent()) {
            passenger.get().setActivated(true);
            if (!tokenUtils.validateToken(token, UserPrinciple.build(passenger.get()))) {
                throw new InvalidInputException("Activation link has expired. Please try registering again. Make sure you activate your account within 5 hours of the registration.");
            }
        } else {
            throw new ObjectNotFoundException("Passenger not found!");
        }
        update(passenger.get());
        return passenger.get().getActivated();
    }
    
}
