package com.security.common;

public record KeycloakValues(String realmId,
                             String realmName,
                             String credentialData,
                             String credentialType,
                             String userLabel,
                             String tokenUrl,
                             String client_id) { }