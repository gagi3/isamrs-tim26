package com.delta.fly.controller;

import com.delta.fly.dto.FriendshipRequestDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.FriendshipRequest;
import com.delta.fly.service.abstraction.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/friendship-request")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestService friendshipRequestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<FriendshipRequest>> getAll() {

        List<FriendshipRequest> requests = friendshipRequestService.findAll();

        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FriendshipRequest> getOne(@PathVariable Long id) throws ObjectNotFoundException {

        FriendshipRequest friendshipRequest = friendshipRequestService.getOne(id);

        return new ResponseEntity<>(friendshipRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/addFriendshipRequest", method = RequestMethod.POST)
    public ResponseEntity<FriendshipRequest> create(@RequestBody FriendshipRequestDTO dto) throws InvalidInputException, ObjectNotFoundException {

        FriendshipRequest newFriendshipRequest = friendshipRequestService.create(dto);

        return new ResponseEntity<>(newFriendshipRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateFriendshipRequest", method = RequestMethod.POST)
    public ResponseEntity<FriendshipRequest> update(@RequestBody FriendshipRequest friendshipRequest) throws ObjectNotFoundException {

        FriendshipRequest updateFriendshipRequest = friendshipRequestService.update(friendshipRequest);

        return new ResponseEntity<>(updateFriendshipRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFriendshipRequest/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ObjectNotFoundException {

        Boolean delete = friendshipRequestService.delete(id);

        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
