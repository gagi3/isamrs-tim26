package com.delta.fly.service.implementation;

import com.delta.fly.dto.PriceListDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompany;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.model.PriceList;
import com.delta.fly.repository.PriceListRepository;
import com.delta.fly.service.abstraction.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository repository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public List<PriceList> findAll() {
        return repository.findAllByDeletedIsFalse();
    }

    @Override
    public PriceList getOne(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Price List with ID: " + id + " doesn't exist!"));
    }

    @Override
    public PriceList getThis() throws ObjectNotFoundException {
        Optional<PriceList> priceList;
        Optional<AirlineCompany> company;
        Optional<AirlineCompanyAdmin> admin;
        try {
            admin = Optional.ofNullable(userDetailsService.getAdmin());
            company = Optional.ofNullable(admin.get().getAirlineCompany());
            priceList = Optional.ofNullable(repository.findByAirlineCompany(company.get()));
            if (!priceList.isPresent()) {
                throw new ObjectNotFoundException("Price List for this company doesn't exist!");
            }
            return priceList.get();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public PriceList create(PriceListDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<PriceList> priceList;
        Optional<AirlineCompany> company;
        Optional<AirlineCompanyAdmin> admin;
        try {
            admin = Optional.ofNullable(userDetailsService.getAdmin());
            company = Optional.ofNullable(admin.get().getAirlineCompany());
            if (!admin.isPresent()) {
                throw new ObjectNotFoundException("Admin not found!");
            } else if (!company.isPresent()) {
                throw new ObjectNotFoundException("Company not found!");
            } else if (repository.findByAirlineCompanyId(company.get().getId()) != null) {
                throw new InvalidInputException("Price List for company with ID: " + company.get().getId() + " already exists!");
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
    public PriceList update(PriceList priceList) throws ObjectNotFoundException, InvalidInputException {
        Optional<PriceList> uPriceList;
        Optional<AirlineCompanyAdmin> admin;
        try {
            admin = Optional.ofNullable(userDetailsService.getAdmin());
            uPriceList = Optional.ofNullable(getOne(priceList.getId()));
            if (!admin.isPresent()) {
                throw new ObjectNotFoundException("Admin not found!");
            } else if (!uPriceList.isPresent()) {
                throw new ObjectNotFoundException("Price List with ID: " + priceList.getId() + " not found!");
            } else if (!admin.get().getAirlineCompany().equals(uPriceList.get().getAirlineCompany())) {
                throw new InvalidInputException("This admin doesn't moderate this company: " + uPriceList.get().getAirlineCompany() + "!");
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
        } catch (InvalidInputException e) {
            e.printStackTrace();
            throw new InvalidInputException(e);
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

}
