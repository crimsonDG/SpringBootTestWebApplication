package com.core.repository;

import com.core.domain.KeycloakEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeycloakEntityRepository extends CrudRepository<KeycloakEntity, String> {

    Page<KeycloakEntity> findAll(Pageable pageable);

    @Query(
            value = "select * from user_entity where username = :username",
            nativeQuery = true
    )
    Optional<KeycloakEntity> findByUsername(String username);

    @Query(
            value = "select * from user_entity order by id asc",
            nativeQuery = true
    )
    Page<KeycloakEntity> ascSorted(Pageable pageable);

    @Query(
            value = "select * from user_entity order by id desc",
            nativeQuery = true
    )
    Page<KeycloakEntity> descSorted(Pageable pageable);
}
