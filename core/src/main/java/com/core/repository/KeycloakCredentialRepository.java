package com.core.repository;

import com.core.domain.KeycloakCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeycloakCredentialRepository extends CrudRepository<KeycloakCredential, String> {
    @Query(
            value = "select * from credential where credential_data = :credentialData",
            nativeQuery = true
    )
    Optional<KeycloakCredential> findByCredentialData(String credentialData);

    @Query(
            value = "select * from credential where secret_data = :secretData",
            nativeQuery = true
    )
    Optional<KeycloakCredential> findBySecretData(String secretData);

    @Query(
            value = "select * from credential where user_id = :userId",
            nativeQuery = true
    )
    Optional<KeycloakCredential> findByUserId(String userId);

}
