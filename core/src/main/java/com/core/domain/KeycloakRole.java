package com.core.domain;

import jakarta.persistence.Column;
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

    @Column(name = "realm_id")
    private String realmId;

    @Column(name = "client_realm_constraint")
    private String clientRealmConstraint;

    @Column(name = "client_role")
    private boolean clientRole;
}
