package com.delta.fly.repository;

import com.delta.fly.model.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {

    List<SystemAdmin> findAllByDeletedIsFalse();
    List<SystemAdmin> findAllByDeletedIsTrue();
    Optional<SystemAdmin> findByUsername(String email);
    Optional<SystemAdmin> findByUsernameAndDeletedIsFalse(String email);
    Optional<SystemAdmin> findByUsernameAndDeletedIsTrue(String email);

}
