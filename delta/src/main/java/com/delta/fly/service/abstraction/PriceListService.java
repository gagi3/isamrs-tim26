package com.delta.fly.service.abstraction;

import com.delta.fly.dto.PriceListDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.PriceList;

import java.util.List;

public interface PriceListService {

    List<PriceList> findAll();

    PriceList getOne(Long id) throws ObjectNotFoundException;

    PriceList getThis() throws ObjectNotFoundException;

    PriceList create(PriceListDTO dto) throws ObjectNotFoundException, InvalidInputException;

    PriceList update(PriceList priceList) throws ObjectNotFoundException, InvalidInputException;

    boolean delete(Long id) throws ObjectNotFoundException;
    //List<PriceList> filterSearch();
}
