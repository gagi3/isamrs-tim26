package com.delta.fly.service.abstraction;

import com.delta.fly.dto.BusinessReportDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.Flight;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.Reservation;
import com.delta.fly.model.Ticket;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Reservation getOne(Long id) throws ObjectNotFoundException;
    Reservation create(Ticket ticket, Passenger passenger) throws InvalidInputException;
    Reservation update(Reservation reservation) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;

    List<Reservation> businessReport(BusinessReportDTO dto) throws ObjectNotFoundException;

    List<Reservation> regenerate() throws InvalidInputException;

    Flight getFlight(Long ID) throws ObjectNotFoundException;
}
