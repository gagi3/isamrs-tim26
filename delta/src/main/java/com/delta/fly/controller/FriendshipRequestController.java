package com.delta.fly.controller;

import com.delta.fly.dto.FriendshipDTO;
import com.delta.fly.dto.FriendshipRequestDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.FriendshipRequest;
import com.delta.fly.model.Passenger;
import com.delta.fly.service.abstraction.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/friendship-request")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<FriendshipRequest>> getAll() {

        List<FriendshipRequest> requests = friendshipRequestService.findAll();

        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FriendshipRequest> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        FriendshipRequest friendshipRequest = friendshipRequestService.getOne(id);

        return new ResponseEntity<>(friendshipRequest, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<FriendshipRequest> create(@RequestBody FriendshipRequestDTO dto) throws InvalidInputException, ObjectNotFoundException {

        FriendshipRequest newFriendshipRequest = friendshipRequestService.create(dto);

        return new ResponseEntity<>(newFriendshipRequest, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<FriendshipRequest> update(@RequestBody FriendshipRequest friendshipRequest) throws ObjectNotFoundException {

        FriendshipRequest updateFriendshipRequest = friendshipRequestService.update(friendshipRequest);

        return new ResponseEntity<>(updateFriendshipRequest, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = friendshipRequestService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public ResponseEntity<Boolean> accept(@RequestBody FriendshipRequest friendshipRequest) throws ObjectNotFoundException {

        Boolean accepted = friendshipRequestService.accept(friendshipRequest);

        return new ResponseEntity<>(accepted, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/get/sent", method = RequestMethod.GET)
    public ResponseEntity<List<FriendshipRequest>> getSent() throws ObjectNotFoundException {

        List<FriendshipRequest> friendshipRequests = friendshipRequestService.getSent();

        return new ResponseEntity<>(friendshipRequests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/get/received", method = RequestMethod.GET)
    public ResponseEntity<List<FriendshipRequest>> getReceived() throws ObjectNotFoundException {

        List<FriendshipRequest> friendshipRequests = friendshipRequestService.getReceived();

        return new ResponseEntity<>(friendshipRequests, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/get/exact", method = RequestMethod.POST)
    public ResponseEntity<FriendshipRequest> getExact(@RequestBody FriendshipDTO dto) throws ObjectNotFoundException {

        FriendshipRequest request = friendshipRequestService.getExact(dto.getFrom(), dto.getTo());

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity<Boolean> reject(@RequestBody FriendshipDTO dto) throws ObjectNotFoundException {

        Boolean rejected = friendshipRequestService.reject(dto.getFrom(), dto.getTo());

        return new ResponseEntity<>(rejected, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Boolean> removeFriend(@RequestBody Passenger remove) throws ObjectNotFoundException {

        Boolean removed = friendshipRequestService.removeFriend(remove);

        return new ResponseEntity<>(removed, HttpStatus.OK);
    }



}
