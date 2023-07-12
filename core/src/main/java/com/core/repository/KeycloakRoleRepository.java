package com.core.repository;

import com.core.domain.KeycloakRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeycloakRoleRepository extends JpaRepository<KeycloakRole, String> {
    Optional<KeycloakRole> findByName(String name);
}
