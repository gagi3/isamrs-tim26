package com.delta.fly.repository;

import com.delta.fly.enumeration.RoleName;
import com.delta.fly.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName name);

}
