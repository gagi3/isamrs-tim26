package com.delta.fly.service.implementation;

import com.delta.fly.dto.SeatDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Airplane;
import com.delta.fly.model.Seat;
import com.delta.fly.repository.SeatRepository;
import com.delta.fly.service.abstraction.AirplaneService;
import com.delta.fly.service.abstraction.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private AirplaneService airplaneService;

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Seat getOne(Long id) throws ObjectNotFoundException {
        return seatRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Seat with ID: " + id + " doesn't exist!"));
    }

    @Override
    public Seat create(SeatDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<Seat> seat;
        Optional<Airplane> airplane;
        try {
            airplane = Optional.ofNullable(airplaneService.getOne(dto.getAirplaneID()));
            if (airplane.isPresent()) {
                seat = Optional.ofNullable(new Seat());
                seat.get().setAirplane(airplane.get());
                seat.get().setDeleted(false);
                seat.get().setColumn(dto.getColNo());
                seat.get().setRow(dto.getRowNo());
                seat.get().setSeatClass(dto.getSeatClass());
                return seatRepository.save(seat.get());
            } else {
                throw new ObjectNotFoundException("Airplane with ID: " + dto.getAirplaneID() + " not found!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Airplane with ID: " + dto.getAirplaneID() + " not found!", ex);
        }
    }

    @Override
    public Seat update(Seat seat) throws ObjectNotFoundException {
        Optional<Seat> uSeat;
        try {
            uSeat = Optional.ofNullable(getOne(seat.getId()));
            if (uSeat.isPresent()) {
                uSeat.get().setSeatClass(seat.getSeatClass());
                uSeat.get().setRow(seat.getRow());
                uSeat.get().setColumn(seat.getColumn());
                uSeat.get().setDeleted(seat.getDeleted());
                uSeat.get().setAirplane(seat.getAirplane());
                return seatRepository.save(seat);
            } else {
                throw new ObjectNotFoundException("Seat with ID: " + seat.getId() + " doesn't exist!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Seat with ID: " + seat.getId() + " doesn't exist!");
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Seat seat = getOne(id);
            seat.setDeleted(true);
            return seat.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Seat with ID: " + id + " doesn't exist!");
        }
    }
}
