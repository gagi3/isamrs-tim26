package com.delta.fly.repository;

import com.delta.fly.model.Flight;
import com.delta.fly.model.PlaceAndTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findFlightsByDeletedFalse();

    List<Flight> findAllByDeletedIsFalse();

    List<Flight> findAllByDeletedIsTrue();

    List<Flight> findAllByAirlineCompany_Id(String name);

    List<Flight> findAllByAirlineCompany_IdAndDeletedIsFalse(String name);

    List<Flight> findAllByAirlineCompany_IdAndDeletedIsTrue(String name);

    List<Flight> findAllByArrival(PlaceAndTime arrival);

    List<Flight> findAllByArrivalThePlace(String place);

    List<Flight> findAllByArrivalAndDeletedIsFalse(PlaceAndTime arrival);

    List<Flight> findAllByArrivalAndDeletedIsTrue(PlaceAndTime arrival);

    List<Flight> findAllByDeparture(PlaceAndTime departure);

    List<Flight> findAllByDepartureThePlace(String place);

    List<Flight> findAllByDepartureAndDeletedIsFalse(PlaceAndTime departure);

    List<Flight> findAlldByDepartureAndDeletedIsTrue(PlaceAndTime departure);


}
