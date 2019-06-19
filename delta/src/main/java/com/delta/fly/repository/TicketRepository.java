package com.delta.fly.repository;

import com.delta.fly.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByDeletedIsFalse();

    List<Ticket> findAllByDeletedIsTrue();

    List<Ticket> findAllByFlight(Flight flight);

    List<Ticket> findAllByFlightAndDeletedIsFalse(Flight flight);

    List<Ticket> findAllByFlightAndDeletedIsTrue(Flight flight);

    List<Ticket> findAllBySeat(Seat seat);

    List<Ticket> findAllBySeatAndDeletedIsFalse(Seat seat);

    List<Ticket> findAllBySeatAndDeletedIsTrue(Seat seat);

    List<Ticket> findAllByPrice(Double price);

    List<Ticket> findAllByPriceAndDeletedIsFalse(Double price);

    List<Ticket> findAllByPriceAndDeletedIsTrue(Double price);

    List<Ticket> findAllByPassenger(Passenger passenger);

    List<Ticket> findAllByPassengerAndDeletedIsFalse(Passenger passenger);

    List<Ticket> findAllByPassengerAndDeletedIsTrue(Passenger passenger);

    List<Ticket> findAllByFlight_AirlineCompany(AirlineCompany company);

    List<Ticket> findAllByFlight_AirlineCompanyAndDeletedIsFalse(AirlineCompany company);

    List<Ticket> findAllByFlight_AirlineCompanyAndDeletedIsTrue(AirlineCompany company);

    List<Ticket> findAllByFlight_AirlineCompany_Name(String name);

    List<Ticket> findAllByFlight_AirlineCompany_NameAndDeletedIsFalse(String name);

    List<Ticket> findAllByFlight_AirlineCompany_NameAndDeletedIsTrue(String name);

    List<Ticket> findAllByFlight_Airplane(Airplane airplane);

    List<Ticket> findAllByFlight_AirplaneAndDeletedIsFalse(Airplane airplane);

    List<Ticket> findAllByFlight_AirplaneAndDeletedIsTrue(Airplane airplane);

    List<Ticket> findAllByFlight_Airplane_Name(String name);

    List<Ticket> findAllByFlight_Airplane_NameAndDeletedIsFalse(String name);

    List<Ticket> findAllByFlight_Airplane_NameAndDeletedIsTrue(String name);

    List<Ticket> findAllByFlight_Arrival(PlaceAndTime arrival);

    List<Ticket> findAllByFlight_ArrivalAndDeletedIsFalse(PlaceAndTime arrival);

    List<Ticket> findAllByFlight_ArrivalAndDeletedIsTrue(PlaceAndTime arrival);

    List<Ticket> findAllByFlight_Arrival_ThePlace(String place);

    List<Ticket> findAllByFlight_Arrival_ThePlaceAndDeletedIsFalse(String place);

    List<Ticket> findAllByFlight_Arrival_ThePlaceAndDeletedIsTrue(String place);

    List<Ticket> findAllByFlight_Arrival_TheTime(Date time);

    List<Ticket> findAllByFlight_Arrival_TheTimeAndDeletedIsFalse(Date time);

    List<Ticket> findAllByFlight_Arrival_TheTimeAndDeletedIsTrue(Date time);

    List<Ticket> findAllByFlight_Departure(PlaceAndTime departure);

    List<Ticket> findAllByFlight_DepartureAndDeletedIsFalse(PlaceAndTime departure);

    List<Ticket> findAllByFlight_DepartureAndDeletedIsTrue(PlaceAndTime departure);

    List<Ticket> findAllByFlight_Departure_ThePlace(String place);

    List<Ticket> findAllByFlight_Departure_ThePlaceAndDeletedIsFalse(String place);

    List<Ticket> findAllByFlight_Departure_ThePlaceAndDeletedIsTrue(String place);

    List<Ticket> findAllByFlight_Departure_TheTime(Date time);

    List<Ticket> findAllByFlight_Departure_TheTimeAndDeletedIsFalse(Date time);

    List<Ticket> findAllByFlight_Departure_TheTimeAndDeletedIsTrue(Date time);

}
