package com.delta.fly.service.implementation;

import com.delta.fly.dto.PlaceAndTimeDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.PlaceAndTime;
import com.delta.fly.repository.PlaceAndTimeRepository;
import com.delta.fly.service.abstraction.PlaceAndTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceAndTimeServiceImpl implements PlaceAndTimeService {

    @Autowired
    private PlaceAndTimeRepository placeAndTimeRepository;

    @Override
    public List<PlaceAndTime> findAll() {
        return placeAndTimeRepository.findAllByDeletedIsFalse();
    }

    @Override
    public PlaceAndTime getOne(Long id) throws ObjectNotFoundException {
        return placeAndTimeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Place and time with ID: " + id + " doesn't exist!"));
    }

    @Override
    public PlaceAndTime create(PlaceAndTimeDTO dto) throws InvalidInputException {
        Optional<PlaceAndTime> placeAndTime;
        try {
            placeAndTime = Optional.ofNullable(new PlaceAndTime());
            placeAndTime.get().setDeleted(false);
            placeAndTime.get().setThePlace(dto.getThePlace());
            if (dto.getTheTime().before(new Date())) {
                throw new InvalidInputException("Date should be in the future!");
            }
            placeAndTime.get().setTheTime(dto.getTheTime());
            return placeAndTimeRepository.save(placeAndTime.get());
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Date should be in the future!", ex);
        }
    }

    @Override
    public PlaceAndTime update(PlaceAndTime placeAndTime) throws ObjectNotFoundException, InvalidInputException {
        Optional<PlaceAndTime> uPlaceAndTime;
        try {
            uPlaceAndTime = Optional.ofNullable(getOne(placeAndTime.getId()));
            if (uPlaceAndTime.isPresent()) {
                if (placeAndTime.getTheTime().before(new Date())) {
                    throw new InvalidInputException("Date should be in the future!");
                }
                uPlaceAndTime.get().setTheTime(placeAndTime.getTheTime());
                uPlaceAndTime.get().setThePlace(placeAndTime.getThePlace());
                uPlaceAndTime.get().setDeleted(placeAndTime.getDeleted());
                return placeAndTimeRepository.save(uPlaceAndTime.get());
            } else {
                throw new ObjectNotFoundException("Place and time with ID: " + placeAndTime.getId() + " doesn't exist!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Place and time with ID: " + placeAndTime.getId() + " doesn't exist!", ex);
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException("Date should be in the future!", ex);
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            PlaceAndTime placeAndTime = getOne(id);
            placeAndTime.setDeleted(true);
            return placeAndTime.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Place and time with ID: " + id + " doesn't exist!");
        }
    }
}
