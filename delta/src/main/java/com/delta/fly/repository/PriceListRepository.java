package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findAllByDeletedIsFalse();
    Optional<PriceList> findByAirlineCompany(AirlineCompany company);
    Optional<PriceList> findByAirlineCompanyId(Long id);
    PriceList findPriceListByAirlineCompany(AirlineCompany company);

}
