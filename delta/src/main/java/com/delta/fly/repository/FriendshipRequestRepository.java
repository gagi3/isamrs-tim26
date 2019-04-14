package com.delta.fly.repository;

import com.delta.fly.model.FriendshipRequest;
import com.delta.fly.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    List<FriendshipRequest> findAllByDeletedIsFalse();
    List<FriendshipRequest> findAllByDeletedIsTrue();
    List<FriendshipRequest> findAllByAcceptedIsTrue();
    List<FriendshipRequest> findAllByAcceptedIsTrueAndDeletedIsFalse();
    List<FriendshipRequest> findAllByAcceptedIsTrueAndDeletedIsTrue();
    List<FriendshipRequest> findAllBySentFrom(Passenger passenger);
    List<FriendshipRequest> findAllBySentFromAndDeletedIsFalse(Passenger passenger);
    List<FriendshipRequest> findAllBySentFromAndDeletedIsTrue(Passenger passenger);
    List<FriendshipRequest> findAllBySentTo(Passenger passenger);
    List<FriendshipRequest> findAllBySentToAndDeletedIsFalse(Passenger passenger);
    List<FriendshipRequest> findAllBySentToAndDeletedIsTrue(Passenger passenger);
    Optional<FriendshipRequest> findBySentFromAndSentTo(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndDeletedIsFalse(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndDeletedIsTrue(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsTrue(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsTrueAndDeletedIsFalse(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsTrueAndDeletedIsTrue(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsFalse(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsFalseAndDeletedIsFalse(Passenger sentFrom, Passenger sentTo);
    Optional<FriendshipRequest> findBySentFromAndSentToAndAcceptedIsFalseAndDeletedIsTrue(Passenger sentFrom, Passenger sentTo);
    List<FriendshipRequest> findAllBySentFromAndAcceptedIsTrue(Passenger passenger);
    List<FriendshipRequest> findAllBySentFromAndAcceptedIsTrueAndDeletedIsFalse(Passenger passenger);
    List<FriendshipRequest> findAllBySentFromAndAcceptedIsTrueAndDeletedIsTrue(Passenger passenger);
    List<FriendshipRequest> findAllBySentToAndAcceptedIsFalse(Passenger passenger);
    List<FriendshipRequest> findAllBySentToAndAcceptedIsFalseAndDeletedIsFalse(Passenger passenger);
    List<FriendshipRequest> findAllBySentToAndAcceptedIsFalseAndDeletedIsTrue(Passenger passenger);

}
