package com.delta.fly.service.abstraction;

import com.delta.fly.dto.SeatDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Seat;

import java.util.List;

public interface SeatService {

        List<Seat> findAll();
        Seat getOne(Long id) throws ObjectNotFoundException;
        Seat create(SeatDTO dto) throws ObjectNotFoundException, InvalidInputException;
        Seat update(Seat seat) throws ObjectNotFoundException;
        boolean delete (Long id) throws ObjectNotFoundException;
        //List<Seat> filterSearch();

}
