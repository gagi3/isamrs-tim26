package com.delta.fly.service.implementation;

import com.delta.fly.dto.FriendshipRequestDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.FriendshipRequest;
import com.delta.fly.model.Passenger;
import com.delta.fly.repository.FriendshipRequestRepository;
import com.delta.fly.service.abstraction.FriendshipRequestService;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    @Autowired
    FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    PassengerService passengerService;

    @Override
    public List<FriendshipRequest> findAll() {
        return friendshipRequestRepository.findAllByDeletedIsFalse();
    }

    @Override
    public FriendshipRequest getOne(Long id) throws ObjectNotFoundException {
        return friendshipRequestRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Friendship request with ID: " + id + " doesn't exist!"));
    }

    @Override
    public FriendshipRequest create(FriendshipRequestDTO dto) throws ObjectNotFoundException, InvalidInputException {
        Optional<Passenger> from;
        Optional<Passenger> to;
        Optional<FriendshipRequest> request;
        try {
            from = Optional.ofNullable(passengerService.getOne(dto.getFromID()));
            to = Optional.ofNullable(passengerService.getOne(dto.getToID()));
            if (!from.isPresent()) {
                throw new ObjectNotFoundException("User with ID: " + dto.getFromID() + " doesn't exist!");
            }
            if (!to.isPresent()) {
                throw new ObjectNotFoundException("User with ID: " + dto.getToID() + " doesn't exist!");
            }
            if (friendshipRequestRepository.findBySentFromAndSentToAndDeletedIsFalse(from.get(), to.get()).isPresent() || friendshipRequestRepository.findBySentFromAndSentToAndDeletedIsFalse(to.get(), from.get()).isPresent()) {
                throw new InvalidInputException("You have already sent a friendship request to this user.");
            }
            if (from.get().getFriends().contains(to.get()) && to.get().getFriends().contains(from.get()) && friendshipRequestRepository.findBySentFromAndSentToAndAcceptedIsTrue(from.get(), to.get()).isPresent()) {
                throw new InvalidInputException("You are already friends!");
            }
            request = Optional.ofNullable(new FriendshipRequest());
            request.get().setSentFrom(from.get());
            request.get().setSentTo(to.get());
            request.get().setDeleted(false);
            request.get().setAccepted(false);
            return friendshipRequestRepository.save(request.get());
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("User not found!");
        } catch (InvalidInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex);
        }
    }

    @Override
    public FriendshipRequest update(FriendshipRequest request) throws ObjectNotFoundException {
        Optional<FriendshipRequest> uRequest;
        try {
            uRequest = Optional.ofNullable(getOne(request.getId()));
            if (uRequest.isPresent()) {
                uRequest.get().setAccepted(request.getAccepted());
                uRequest.get().setDeleted(request.getDeleted());
                uRequest.get().setSentFrom(request.getSentFrom());
                uRequest.get().setSentTo(request.getSentTo());
                return friendshipRequestRepository.save(uRequest.get());
            } else {
                throw new ObjectNotFoundException("Friendship request with ID: " + request.getId() + " doesn't exist!");
            }
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Friendship request doesn't exist!");
        }
    }

    @Override
    public boolean delete(Long id) throws ObjectNotFoundException {
        try {
            FriendshipRequest request = getOne(id);
            request.setDeleted(true);
            return request.getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException("Friendship request with ID: " + id + " doesn't exist!", ex);
        }
    }
}
