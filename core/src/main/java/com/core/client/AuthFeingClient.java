package com.core.client;

import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service")
public interface AuthFeingClient {

    @RequestMapping(method = RequestMethod.POST, value = "/auth/token")
    UserTokenDto auth(@RequestBody UserAccessDto userAccessDto);
}
