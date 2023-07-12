package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakCredentialDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private String id;

    private int priority;

    private byte[] salt;

    private String type;

    private String credentialData;

    private String secretData;

    private String userLabel;

    private String userId;

    private long createdDate;
}
