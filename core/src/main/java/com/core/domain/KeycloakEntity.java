package com.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class KeycloakEntity {

    @Id
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<KeycloakRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeycloakCredential> credentials;

    public void removeCredential(KeycloakCredential credential){
        this.credentials.remove(credential);
    }

    public void removeRole(KeycloakRole role) {
        this.roles.remove(role);
    }
}
