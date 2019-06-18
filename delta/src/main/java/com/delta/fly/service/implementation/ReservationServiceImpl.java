package com.delta.fly.service.implementation;

import com.delta.fly.dto.BusinessReportDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.repository.ReservationRepository;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import com.delta.fly.service.abstraction.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AirlineCompanyService airlineCompanyService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getOne(Long id) throws ObjectNotFoundException {
        return reservationRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Reservation with ID: " + id + " doesn't exist!"));
    }

    @Override
    public Reservation create(Ticket ticket, Passenger passenger) throws InvalidInputException {
        Optional<Reservation> reservation = Optional.of(new Reservation());
        try {
            reservation = Optional.ofNullable(reservationRepository.findByPassengerAndTicket(passenger, ticket));
            if (reservation.isPresent()) {
                throw new InvalidInputException("Reservation already exists.");
            }
            if (!ticket.getPassenger().equals(passenger)) {
                throw new InvalidInputException("Passenger data mismatch.");
            }
            if (!ticket.getConfirmed()) {
                throw new InvalidInputException("Reservation not confirmed!");
            }
            reservation = Optional.of(new Reservation());
            reservation.get().setTicket(ticket);
            reservation.get().setPassenger(passenger);
            reservation.get().setReservationDate(new Date());
            reservation.get().setDeleted(false);
            return reservationRepository.save(reservation.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        }
    }

    @Override
    public Reservation update(Reservation reservation) throws ObjectNotFoundException {
        Optional<Reservation> uReservation = Optional.empty();
        try {
            uReservation = Optional.ofNullable(getOne(reservation.getId()));
            if (!uReservation.isPresent()) {
                throw new ObjectNotFoundException("Reservation doesn't exist.");
            }
            uReservation = Optional.of(new Reservation());
            uReservation.get().setTicket(reservation.getTicket());
            uReservation.get().setPassenger(reservation.getPassenger());
            uReservation.get().setReservationDate(reservation.getReservationDate());
            uReservation.get().setDeleted(reservation.getDeleted());
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
        }
        return reservationRepository.save(uReservation.get());
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            Reservation reservation = getOne(id);
            reservation.setDeleted(true);
            reservationRepository.save(reservation);
            return reservation.getDeleted();
        } catch (ObjectNotFoundException ex) {
            throw new ObjectNotFoundException("Reservation with ID: " + id + " doesn't exist!");
        }
    }

    @Override
    public List<Reservation> businessReport(BusinessReportDTO dto) throws ObjectNotFoundException {
        Optional<AirlineCompany> company;
        Optional<AirlineCompanyAdmin> admin;
        try {
            admin = Optional.ofNullable(userDetailsService.getAdmin());
            if (!admin.isPresent()) {
                throw new ObjectNotFoundException("Admin doesn't exist.");
            }
            company = Optional.ofNullable(admin.get().getAirlineCompany());
            List<Reservation> reservations;
            reservations = reservationRepository.findAllByReservationDateAfterAndReservationDateBeforeAndTicketFlightAirlineCompany(
                    dto.getAfter(),
                    dto.getBefore(),
                    company.get()
            );
            if (reservations == null || reservations.isEmpty()) {
                throw new ObjectNotFoundException("No reservations for the selected interval and company");
            }
            for (Reservation r : reservations) {
                if (r.getTicket().getFlight().getArrival().getTheTime().after(new Date())) {
                    reservations.remove(r);
                }
            }
            return reservations;
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public List<Reservation> regenerate() throws InvalidInputException {
        List<Reservation> reservations = new ArrayList<>();
        for (AirlineCompany company : airlineCompanyService.findAll()) {
            for (Flight flight : company.getFlights()) {
                for (Ticket ticket: flight.getTickets()) {
                    if (ticket.getPassenger() != null && ticket.getConfirmed()) {
                        reservations.add(create(ticket, ticket.getPassenger()));
                    }
                }
            }
        }
        return reservations;
    }

    @Override
    public Flight getFlight(Long ID) throws ObjectNotFoundException {
        Optional<Reservation> reservation;
        try {
            reservation = Optional.ofNullable(getOne(ID));
            if (!reservation.isPresent()) {
                throw new ObjectNotFoundException("Reservation doesn't exist!");
            }
            return reservation.get().getTicket().getFlight();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Reservation getByTicket(Ticket ticket) throws ObjectNotFoundException {
        return reservationRepository.findByTicket(ticket).orElseThrow(() -> new ObjectNotFoundException("Reservation doesn't exist."));
    }
}
