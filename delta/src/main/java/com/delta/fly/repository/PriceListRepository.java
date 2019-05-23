package com.delta.fly.repository;

import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findAllByDeletedIsFalse();
    PriceList findByAirlineCompany(AirlineCompany company);
    PriceList findByAirlineCompanyId(Long id);

}
