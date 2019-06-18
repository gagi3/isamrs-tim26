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
    private FriendshipRequestRepository friendshipRequestRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

    @Override
    public Boolean accept(FriendshipRequest request) throws ObjectNotFoundException {
        Optional<Passenger> you;
        Optional<Passenger> from;
        Optional<Passenger> to;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            FriendshipRequest accepted = getOne(request.getId());
            accepted.setAccepted(true);
            from = Optional.ofNullable(accepted.getSentFrom());
            to = Optional.ofNullable(accepted.getSentTo());
            if (!from.isPresent() || !to.isPresent() || !you.isPresent()) {
                throw new ObjectNotFoundException("No passenger!");
            }
            if (!to.get().equals(you.get())) {
                throw new ObjectNotFoundException("You can not accept others' requests.");
            }
            from.get().getFriends().add(to.get());
            to.get().getFriends().add(from.get());
            passengerService.update(from.get());
            passengerService.update(to.get());
            update(accepted);
            return accepted.getAccepted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public List<FriendshipRequest> getSent() throws ObjectNotFoundException {
        Optional<Passenger> you;
        Optional<List<FriendshipRequest>> requests;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            if (!you.isPresent()) {
                throw new ObjectNotFoundException("No passenger!");
            }
            requests = Optional.ofNullable(friendshipRequestRepository.findAllBySentFrom(you.get()));
            if (!requests.isPresent()) {
                throw new ObjectNotFoundException("You have not sent any requests!");
            }
            if (requests.get().isEmpty()) {
                return null;
            }
            return requests.get();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public List<FriendshipRequest> getReceived() throws ObjectNotFoundException {
        Optional<Passenger> you;
        Optional<List<FriendshipRequest>> requests;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            if (!you.isPresent()) {
                throw new ObjectNotFoundException("No passenger!");
            }
            requests = Optional.ofNullable(friendshipRequestRepository.findAllBySentTo(you.get()));
            if (!requests.isPresent()) {
                throw new ObjectNotFoundException("You have not sent any requests!");
            }
            if (requests.get().isEmpty()) {
                return null;
            }
            return requests.get();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public FriendshipRequest getExact(Passenger from, Passenger to) throws ObjectNotFoundException {
        Optional<Passenger> you;
        Optional<Passenger> pFrom;
        Optional<Passenger> pTo;
        Optional<FriendshipRequest> request;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            pFrom = Optional.ofNullable(passengerService.getOne(from.getId()));
            pTo = Optional.ofNullable(passengerService.getOne(to.getId()));
            if (!pFrom.isPresent() || !pTo.isPresent() || !you.isPresent()) {
                throw new ObjectNotFoundException("No passenger!");
            }
            if (!pTo.get().equals(you.get())) {
                throw new ObjectNotFoundException("You can not see others' requests.");
            }
            request = friendshipRequestRepository.findBySentFromAndSentTo(pFrom.get(), pTo.get());
            if (!request.isPresent()) {
                throw new ObjectNotFoundException("No request found!");
            }
            return request.get();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Boolean reject(Passenger from, Passenger to) throws ObjectNotFoundException {
        Optional<FriendshipRequest> request;
        try {
            request = Optional.ofNullable(getExact(from, to));
            if (!request.isPresent()) {
                throw new ObjectNotFoundException("No request!");
            }
            request.get().setAccepted(false);
            request.get().setDeleted(true);
            return request.get().getAccepted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }

    @Override
    public Boolean removeFriend(Passenger remove) throws ObjectNotFoundException {
        Optional<Passenger> you;
        Optional<Passenger> other;
        Optional<FriendshipRequest> request;
        try {
            you = Optional.ofNullable(userDetailsService.getPassenger());
            other = Optional.ofNullable(passengerService.getOne(remove.getId()));
            if (!other.isPresent() || !you.isPresent()) {
                throw new ObjectNotFoundException("No passenger!");
            }
            request = Optional.ofNullable(getExact(you.get(), other.get()));
            if (!request.isPresent()) {
                request = Optional.ofNullable(getExact(other.get(), you.get()));
                if (!request.isPresent()) {
                    throw new ObjectNotFoundException("No request!");
                }
            }
            you.get().getFriends().remove(other.get());
            other.get().getFriends().remove(you.get());
            passengerService.update(you.get());
            passengerService.update(other.get());
            request.get().setAccepted(false);
            request.get().setDeleted(true);
            return request.get().getDeleted();
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ObjectNotFoundException(ex);
        }
    }
}
