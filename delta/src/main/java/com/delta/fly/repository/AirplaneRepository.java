package com.delta.fly.repository;

import com.delta.fly.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    List<Airplane> findAllByDeletedIsFalse();

    List<Airplane> findAllByDeletedIsTrue();

    Optional<Airplane> findByName(String name);

    Optional<Airplane> findByNameAndDeletedIsFalse(String name);

    Optional<Airplane> findByNameAndDeletedIsTrue(String name);

    Optional<Airplane> findByAirlineCompany_Id(Long id);

    Optional<Airplane> findByAirlineCompany_IdAndDeletedIsFalse(Long id);

    Optional<Airplane> findByAirlineCompany_IdAndDeletedIsTrue(Long id);

}
