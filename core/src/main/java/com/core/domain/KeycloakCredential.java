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

    @Column(name = "credential_data")
    private String credentialData;

    @Column(name = "secret_data")
    private String secretData;

    @Column(name = "user_label")
    private String userLabel;

    @Column(name = "created_date")
    private long createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private KeycloakEntity user;
}
