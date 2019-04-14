package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.AirlineCompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineCompanyAdminRepository extends JpaRepository<AirlineCompanyAdmin, Long> {

    List<AirlineCompanyAdmin> findAllByDeletedIsFalse();
    List<AirlineCompanyAdmin> findAllByDeletedIsTrue();
    Optional<AirlineCompanyAdmin> findByUsername(String email);
    Optional<AirlineCompanyAdmin> findByUsernameAndDeletedIsFalse(String email);
    Optional<AirlineCompanyAdmin> findByUsernameAndDeletedIsTrue(String email);

}
