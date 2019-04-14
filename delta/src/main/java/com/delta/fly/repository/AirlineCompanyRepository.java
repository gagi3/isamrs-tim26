package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineCompanyRepository extends JpaRepository<AirlineCompany, Long> {

    List<AirlineCompany> findAllByDeletedIsFalse();
    List<AirlineCompany> findAllByDeletedIsTrue();
    Optional<AirlineCompany> findByAddress(String address);
    Optional<AirlineCompany> findByAddressAndDeletedIsFalse(String address);
    Optional<AirlineCompany> findByAddressAndDeletedIsTrue(String address);
    Optional<AirlineCompany> findByName(String name);
    Optional<AirlineCompany> findByNameAndDeletedIsFalse(String name);
    Optional<AirlineCompany> findByNameAndDeletedIsTrue(String name);

}
