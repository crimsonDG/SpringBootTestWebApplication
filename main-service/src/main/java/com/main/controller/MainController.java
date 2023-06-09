package com.main.controller;

import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import com.security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// request mapping /admin
@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "basic"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class MainController {

    @Autowired
    UserService userService;

    //Main page
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/token")
    public UserTokenDto auth(@RequestBody UserAccessDto userAccessDto) {
        return userService.authUser(userAccessDto);
    }
}
