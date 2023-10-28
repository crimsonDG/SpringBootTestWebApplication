package com.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "keycloak_role")
public class KeycloakRole {
    @Id
    private String id;

    private String name;

    private String description;

    private String client;

    private String realm;

    private String realmId;

    private String clientRealmConstraint;

    private boolean clientRole;
}
