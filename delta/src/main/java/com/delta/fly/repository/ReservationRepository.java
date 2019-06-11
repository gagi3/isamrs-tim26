package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.Reservation;
import com.delta.fly.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByReservationDateAfterAndReservationDateBeforeAndTicketFlightAirlineCompany(Date after, Date before, AirlineCompany company);
    Reservation findByPassengerAndTicket(Passenger passenger, Ticket ticket);

}
