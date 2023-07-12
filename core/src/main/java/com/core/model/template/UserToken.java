package com.core.model.template;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("expires_in")
    private int expiresIn;

    @JsonAlias("refresh_expires_in")
    private long refreshExpiresIn;

    @JsonAlias("refresh_token")
    private String refreshToken;

    @JsonAlias("token_type")
    private String tokenType;

    @JsonAlias("non-before-policy")
    private long nonBeforePolicy;

    @JsonAlias("session_state")
    private String sessionState;

    private String scope;
}
