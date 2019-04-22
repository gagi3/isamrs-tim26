package com.delta.fly.repository;

import com.delta.fly.model.Passenger;
import com.delta.fly.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByTicketsContains(Ticket ticket);
    Optional<Passenger> findByTicketsContainsAndDeletedIsFalse(Ticket ticket);
    Optional<Passenger> findByTicketsContainsAndDeletedIsTrue(Ticket ticket);
    Optional<Passenger> findByFriendsContains(Passenger friend);
    Optional<Passenger> findByFriendsContainsAndDeletedIsFalse(Passenger friend);
    Optional<Passenger> findByFriendsContainsAndDeletedIsTrue(Passenger friend);
    // TODO: Friends in common.
    List<Passenger> findAllByDeletedIsFalse();
    List<Passenger> findAllByDeletedIsTrue();
    Optional<Passenger> findByUsername(String email);
    Optional<Passenger> findByUsernameAndDeletedIsFalse(String email);
    Optional<Passenger> findByUsernameAndDeletedIsTrue(String email);

}
