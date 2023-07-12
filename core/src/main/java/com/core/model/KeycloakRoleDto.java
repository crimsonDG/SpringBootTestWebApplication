package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakRoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private String id;

    private String name;

    private String description;

    private String client;

    private String realm;

    private String realmId;

    private String clientRealmConstraint;

    private boolean clientRole;
}
