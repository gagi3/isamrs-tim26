package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineCompanyRepository extends JpaRepository<AirlineCompany, Long> {
}
