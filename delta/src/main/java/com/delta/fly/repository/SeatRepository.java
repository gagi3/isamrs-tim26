package com.delta.fly.repository;

import com.delta.fly.enumeration.Class;
import com.delta.fly.model.Airplane;
import com.delta.fly.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByDeletedIsFalse();

    List<Seat> findAllByDeletedIsTrue();

    Optional<Seat> findByAirplaneAndRowAndColumn(Airplane airplane, Integer row, Integer column);

    Optional<Seat> findByAirplaneAndRowAndColumnAndDeletedIsFalse(Airplane airplane, Integer row, Integer column);

    Optional<Seat> findByAirplaneAndRowAndColumnAndDeletedIsTrue(Airplane airplane, Integer row, Integer column);

    List<Seat> findAllByAirplane(Airplane airplane);

    List<Seat> findAllByAirplaneAndDeletedIsFalse(Airplane airplane);

    List<Seat> findAllByAirplaneAndDeletedIsTrue(Airplane airplane);

    List<Seat> findAllByAirplaneAndSeatClass(Airplane airplane, Class seatClass);

    List<Seat> findAllByAirplaneAndSeatClassAndDeletedIsFalse(Airplane airplane, Class seatClass);

    List<Seat> findAllByAirplaneAndSeatClassAndDeletedIsTrue(Airplane airplane, Class seatClass);

}
