package com.delta.fly.service.implementation;

import com.delta.fly.dto.AirlineCompanyRegistrationDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompany;
import com.delta.fly.repository.AirlineCompanyRepository;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineCompanyServiceImpl implements AirlineCompanyService {

    @Autowired
    AirlineCompanyRepository airlineCompanyRepository;

    @Override
    public List<AirlineCompany> findAll() {
        return airlineCompanyRepository.findAllByDeletedIsFalse();
    }

    @Override
    public AirlineCompany getOne(Long id) throws ObjectNotFoundException {
        return airlineCompanyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Company with ID: " + id + " not found!"));
    }

    @Override
    public AirlineCompany create(AirlineCompanyRegistrationDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<AirlineCompany> company;
        try {
            if (airlineCompanyRepository.findByAddress(dto.getAddress()).isPresent()) {
                throw new InvalidInputException("Address " + dto.getAddress() +  " is occupied!");
            } else if (airlineCompanyRepository.findByName(dto.getName()).isPresent()) {
                throw new InvalidInputException("Name " + dto.getName() + " in use!");
            } else {
                company = Optional.of(new AirlineCompany());
                company.get().setDeleted(false);
                company.get().setPriceByKm(dto.getPriceByKm());
                company.get().setName(dto.getName());
                company.get().setLuggagePriceByItem(dto.getLuggagePriceByItem());
                company.get().setDestinations(dto.getDestinations());
                company.get().setDescription(dto.getDescription());
                company.get().setAddress(dto.getAddress());
                company.get().setFlights(new ArrayList<>());
                company.get().setDiscountedTickets(new ArrayList<>());
                company.get().setAirplanes(new ArrayList<>());
                return airlineCompanyRepository.save(company.get());
            }
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex.getMessage(), ex);
        }
    }

    @Override
    public AirlineCompany update(AirlineCompany company) throws ObjectNotFoundException {
        Optional<AirlineCompany> uCompany;
        try {
            uCompany = Optional.ofNullable(getOne(company.getId()));
            if (uCompany.isPresent() && !company.getDeleted()) {
                uCompany.get().setAddress(company.getAddress());
                uCompany.get().setAirplanes(company.getAirplanes());
                uCompany.get().setDescription(company.getDescription());
                uCompany.get().setDestinations(company.getDestinations());
                uCompany.get().setDiscountedTickets(company.getDiscountedTickets());
                uCompany.get().setFlights(company.getFlights());
                uCompany.get().setLuggagePriceByItem(company.getLuggagePriceByItem());
                uCompany.get().setName(company.getName());
                uCompany.get().setPriceByKm(company.getPriceByKm());
                uCompany.get().setDeleted(company.getDeleted());
            } else {
                throw new ObjectNotFoundException("Bad ID!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Company with ID: " + company.getId() + " not found!", ex);
        }
        return airlineCompanyRepository.save(uCompany.get());
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            AirlineCompany company = getOne(id);
            company.setDeleted(true);
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Company with ID: " + id + " not found!", ex);
        }
        return true;
    }

}
