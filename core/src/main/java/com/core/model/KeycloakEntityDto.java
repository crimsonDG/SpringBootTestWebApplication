package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakEntityDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private String id;

    private String username;

    private String email;

    private String emailConstraint;

    private String lastName;

    private String firstName;

    private String realmId;

    private String serviceAccountClientLink;

    private String federationLink;

    private boolean emailVerified;

    private long createdTimestamp;

    private boolean enabled;

    private int notBefore;

    private Set<KeycloakRoleDto> roles = new HashSet<>();

    private List<KeycloakCredentialDto> credentials;

    public void removeCredential(KeycloakCredentialDto credential){
        this.credentials.remove(credential);
    }

    public void removeRole(KeycloakRoleDto role) {
        this.roles.remove(role);
    }
}
