package com.delta.fly.service.abstraction;

import com.delta.fly.dto.FriendshipRequestDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.FriendshipRequest;

import java.util.List;

public interface FriendshipRequestService {

    List<FriendshipRequest> findAll();
    FriendshipRequest getOne(Long id) throws ObjectNotFoundException;
    FriendshipRequest create(FriendshipRequestDTO dto) throws ObjectNotFoundException, InvalidInputException;
    FriendshipRequest update(FriendshipRequest request) throws ObjectNotFoundException;
    boolean delete (Long id) throws ObjectNotFoundException;
    //List<FriendshipRequest> filterSearch();
    
}
