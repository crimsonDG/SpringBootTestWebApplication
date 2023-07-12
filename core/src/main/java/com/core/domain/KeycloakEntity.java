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

    @Column(name = "email_constraint")
    private String emailConstraint;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "realm_id")
    private String realmId;

    @Column(name = "service_account_client_link")
    private String serviceAccountClientLink;

    @Column(name = "federation_link")
    private String federationLink;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "created_timestamp")
    private long createdTimestamp;

    private boolean enabled;

    @Column(name = "not_before")
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
