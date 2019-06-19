package com.delta.fly.repository;

import com.delta.fly.model.PlaceAndTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PlaceAndTimeRepository extends JpaRepository<PlaceAndTime, Long> {

    List<PlaceAndTime> findAllByDeletedIsFalse();

    List<PlaceAndTime> findAllByDeletedIsTrue();

    List<PlaceAndTime> findAllByThePlaceAndTheTime(String place, Date time);

    List<PlaceAndTime> findAllByThePlaceAndTheTimeAndDeletedIsFalse(String place, Date time);

    List<PlaceAndTime> findAllByThePlaceAndTheTimeAndDeletedIsTrue(String place, Date time);

    List<PlaceAndTime> findAllByThePlace(String place);

    List<PlaceAndTime> findAllByThePlaceAndDeletedIsFalse(String place);

    List<PlaceAndTime> findAllByThePlaceAndDeletedIsTrue(String place);

    List<PlaceAndTime> findAllByTheTime(Date time);

    List<PlaceAndTime> findAllByTheTimeAndDeletedIsFalse(Date time);

    List<PlaceAndTime> findAllByTheTimeAndDeletedIsTrue(Date time);

}
