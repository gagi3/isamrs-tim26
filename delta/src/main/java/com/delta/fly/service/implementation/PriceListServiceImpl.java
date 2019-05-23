package com.delta.fly.service.implementation;

import com.delta.fly.dto.PriceListDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.*;
import com.delta.fly.repository.AirlineCompanyAdminRepository;
import com.delta.fly.repository.PriceListRepository;
import com.delta.fly.service.abstraction.AirlineCompanyService;
import com.delta.fly.service.abstraction.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository repository;

    @Autowired
    private AirlineCompanyService airlineCompanyService;

    @Autowired
    AirlineCompanyAdminRepository adminRepository;

    @Override
    public List<PriceList> findAll() {
        return repository.findAllByDeletedIsFalse();
    }

    @Override
    public PriceList getOne(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Price List with ID: " + id + " doesn't exist!"));
    }

    @Override
    public PriceList create(PriceListDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<PriceList> priceList;
        Optional<AirlineCompany> company;
        Optional<AirlineCompanyAdmin> admin;
        try {
            company = Optional.ofNullable(airlineCompanyService.getOne(dto.getCompanyID()));
            admin = adminRepository.findByUsername(getUser());
            if (repository.findByAirlineCompanyId(dto.getCompanyID()) != null) {
                throw new InvalidInputException("Price List for company with ID: " + dto.getCompanyID() + " already exists!");
            } else if (!company.isPresent()) {
                throw new ObjectNotFoundException("Company with ID: " + dto.getCompanyID() + " not found!");
            } else if (!admin.isPresent()) {
                throw new ObjectNotFoundException("Admin with email: " + dto.getCompanyID() + " not found!");
            } else if (admin.get().getAirlineCompany() != company.get()) {
                throw new InvalidInputException("This admin doesn't moderate this company: " + company.get().getName() + "!");
            } else {
                priceList = Optional.of(new PriceList());
                priceList.get().setAirlineCompany(company.get());
                priceList.get().setDeleted(false);
                priceList.get().setPriceByKm(dto.getPriceByKm());
                priceList.get().setPriceByLuggageItem(dto.getPriceByLuggageItem());
                priceList.get().setBusinessClassPriceCoefficient(dto.getBusinessClassPriceCoefficient());
                priceList.get().setEconomyClassPriceCoefficient(dto.getEconomyClassPriceCoefficient());
                priceList.get().setFirstClassPriceCoefficient(dto.getFirstClassPriceCoefficient());
                priceList.get().setDiscountPercentage(dto.getDiscountPercentage());
                return repository.save(priceList.get());
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        }
    }

    @Override
    public PriceList update(PriceList priceList) throws ObjectNotFoundException {
        Optional<PriceList> uPriceList;
        try {
            uPriceList = Optional.ofNullable(getOne(priceList.getId()));
            if (!uPriceList.isPresent()) {
                throw new ObjectNotFoundException("Price List with ID: " + priceList.getId() + " not found!");
            } else {
                uPriceList.get().setAirlineCompany(priceList.getAirlineCompany());
                uPriceList.get().setDeleted(priceList.getDeleted());
                uPriceList.get().setPriceByKm(priceList.getPriceByKm());
                uPriceList.get().setPriceByLuggageItem(priceList.getPriceByLuggageItem());
                uPriceList.get().setBusinessClassPriceCoefficient(priceList.getBusinessClassPriceCoefficient());
                uPriceList.get().setEconomyClassPriceCoefficient(priceList.getEconomyClassPriceCoefficient());
                uPriceList.get().setFirstClassPriceCoefficient(priceList.getFirstClassPriceCoefficient());
                uPriceList.get().setDiscountPercentage(priceList.getDiscountPercentage());
                return repository.save(uPriceList.get());
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            PriceList priceList = getOne(id);
            priceList.setDeleted(true);
            return priceList.getDeleted();
        } catch (ObjectNotFoundException ex) {
            throw new ObjectNotFoundException("Price List with ID: " + id + " doesn't exist!");
        }
    }

    private String getUser() {
        UserPrinciple details = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getUsername();
    }
}
