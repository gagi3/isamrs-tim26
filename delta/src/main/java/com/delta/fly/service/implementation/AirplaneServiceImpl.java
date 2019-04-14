package com.delta.fly.service.implementation;

import com.delta.fly.dto.AirplaneDTO;
import com.delta.fly.dto.SeatDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Airplane;
import com.delta.fly.model.Seat;
import com.delta.fly.repository.AirplaneRepository;
import com.delta.fly.repository.SeatRepository;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import com.delta.fly.service.abstraction.AirplaneService;
import com.delta.fly.service.abstraction.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirlineCompanyService airlineCompanyService;

    @Autowired
    SeatService seatService;

    @Override
    public List<Airplane> findAll() {
        return airplaneRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Airplane getOne(Long id) throws ObjectNotFoundException {
        return airplaneRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Airplane with ID: " + id + " not found!"));
    }

    @Override
    public Airplane create(AirplaneDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<Airplane> airplane;
        try {
            if (!airplaneRepository.findByName(dto.getName()).isPresent()) {
                airplane = Optional.of(new Airplane());
                airplane.get().setName(dto.getName());
                airplane.get().setDeleted(false);
                try {
                    airlineCompanyService.getOne(dto.getCompanyID());
                } catch (ObjectNotFoundException ex) {
                    throw new ObjectNotFoundException("Airline company not found!", ex);
                }
                List<Seat> seats = new ArrayList<>();
                for (SeatDTO seatDTO : dto.getSeats()) {
                    Seat s = seatService.create(seatDTO);
                    seats.add(s);
                }
                airplane.get().setSeats(seats);
            } else {
                throw new InvalidInputException("Name must be unique!");
            }
            return airplaneRepository.save(airplane.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Airplane with name: " + dto.getName() + " already exists!", ex);
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Airline company with ID: " + dto.getCompanyID() + " doesn't exist!", ex);
        }
    }

    @Override
    public Airplane update(Airplane airplane) throws ObjectNotFoundException {
        Optional<Airplane> uAirplane;
        try {
            uAirplane = Optional.ofNullable(getOne(airplane.getId()));
            if (uAirplane.isPresent() && !uAirplane.get().getDeleted()) {
                uAirplane.get().setName(airplane.getName());
                uAirplane.get().setSeats(airplane.getSeats());
                uAirplane.get().setAirlineCompany(airplane.getAirlineCompany());
                uAirplane.get().setDeleted(airplane.getDeleted());
                return airplaneRepository.save(uAirplane.get());
            } else {
                throw new ObjectNotFoundException("Bad ID!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Airplane with ID: " + airplane.getId() + " not found!", ex);
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Airplane airplane = getOne(id);
            airplane.setDeleted(true);
            return airplane.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Airplane with ID: " + id + " not found!", ex);
        }
    }

}
