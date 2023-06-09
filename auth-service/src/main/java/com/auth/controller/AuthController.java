package com.auth.controller;

import com.auth.rabbitmq.AuthUserSender;
import com.auth.rabbitmq.RegisteredUserSender;
import com.core.model.UserDto;
import com.security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//Request mapping /auth
@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "basic"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUserSender authUserSender;

    @Autowired
    private RegisteredUserSender registeredUserSender;

    @PostMapping("/registration")
    public UserDto registerUser(@RequestBody UserDto userDto) throws IOException, TimeoutException {
        if (!userService.saveUser(userDto)) {
            System.out.println("The login already exists");
            return null;
        }
        UserDto currentUser = userService.findUserByLogin(userDto.getLogin());
        registeredUserSender.sendRegisteredUser(currentUser);

        return currentUser;
    }

// Todo: adapt for Keycloak
//
//    @PostMapping("/token")
//    public UserTokenDto auth(@RequestBody UserAccessDto userAccessDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userAccessDto.getLogin(), userAccessDto.getPassword()));
//
//        UserDto currentUser = userService.findUserByLogin(userAccessDto.getLogin());
//        authUserSender.sendAuthUser(currentUser);
//    }

}
