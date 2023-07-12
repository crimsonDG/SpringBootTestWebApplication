package com.core.client;

import com.core.client.config.AuthFeignConfig;
import com.core.model.template.UserAccess;
import com.core.model.template.UserToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", configuration = AuthFeignConfig.class)
public interface AuthFeignClient {

    @PostMapping("auth/keycloak/token")
    UserToken auth(@RequestBody UserAccess userAccess);
}
