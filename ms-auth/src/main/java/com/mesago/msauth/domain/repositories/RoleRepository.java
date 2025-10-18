package com.mesago.msauth.domain.repositories;

import com.mesago.msauth.domain.entities.Role;
import com.mesago.msauth.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
