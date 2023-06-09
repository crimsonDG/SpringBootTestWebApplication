package com.core.client;

import com.core.client.config.AuthFeignConfig;
import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Todo: make new client with new functional
@FeignClient(name = "auth-service", configuration = AuthFeignConfig.class)
public interface AuthFeignClient {

    @PostMapping("/auth/token")
    UserTokenDto auth(@RequestBody UserAccessDto userAccessDto);
}
