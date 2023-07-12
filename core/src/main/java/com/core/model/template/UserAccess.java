package com.core.model.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccess {
    private String grantType;
    private String clientId;
    private String username;
    private String password;

    public MultiValueMap<String, String> toMultiValueMap() {
        return new LinkedMultiValueMap<>() {{
            add("grant_type", grantType);
            add("client_id", clientId);
            add("username", username);
            add("password", password);
        }};
    }
}
