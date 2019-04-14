package com.delta.fly.service.implementation;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.enumeration.RoleName;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.Role;
import com.delta.fly.repository.PassengerRepository;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    PassengerRepository passengerRepository;

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Passenger getOne(Long id) throws ObjectNotFoundException {
        return passengerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Admin with ID: " + id + " not found!"));
    }

    @Override
    public Passenger create(RegisterDTO dto) throws InvalidInputException {
        Optional<Passenger> passenger;
        try {
            passenger = passengerRepository.findByUsername(dto.getUsername());
            if (!passenger.isPresent()) {
                passenger = Optional.ofNullable(new Passenger());
                passenger.get().setUsername(dto.getUsername());
                passenger.get().setPassword(dto.getPassword());
                passenger.get().setFirstName(dto.getFirstName());
                passenger.get().setLastName(dto.getLastName());
                passenger.get().setPhoneNumber(dto.getPhoneNumber());
                passenger.get().setCity(dto.getCity());
                passenger.get().setFriends(new ArrayList<>());
                passenger.get().setTickets(new ArrayList<>());
                passenger.get().setRoles(new ArrayList<Role>() {{ add(new Role(RoleName.ROLE_PASSENGER)); }} );
                passenger.get().setDeleted(false);
            } else {
                throw new InvalidInputException("Bad email address.");
            }
            return passengerRepository.save(passenger.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Admin with email: " + dto.getUsername() + " already exists!", ex);
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
                uPassenger.get().setPassword(passenger.getPassword());
                uPassenger.get().setPhoneNumber(passenger.getPhoneNumber());
                uPassenger.get().setTickets(passenger.getTickets());
                uPassenger.get().setFriends(passenger.getFriends());
                uPassenger.get().setRoles(passenger.getRoles());
                uPassenger.get().setUsername(passenger.getUsername());
            } else {
                throw new ObjectNotFoundException("Bad ID");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Admin with ID: " + passenger.getId() + " not found!", ex);
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
            throw new ObjectNotFoundException("Admin with ID: " + id + " not found!", ex);
        }
    }
    
}
