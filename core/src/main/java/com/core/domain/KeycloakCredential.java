package com.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credential")
public class KeycloakCredential {

    @Id
    private String id;

    private int priority;

    private byte[] salt;

    private String type;

    private String credentialData;

    private String secretData;

    private String userLabel;

    private long createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private KeycloakEntity user;
}
