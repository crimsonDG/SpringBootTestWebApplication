package com.auth.controller;

import com.core.model.template.UserAccessDto;
import com.core.model.UserDto;
import com.core.model.template.UserTokenDto;
import com.security.service.UserService;
import com.security.token.JwtUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registration")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        if (!userService.saveUser(userDto)) {
            System.out.println("The login already exists");
            return null;
        }
        return userService.findUserByLogin(userDto.getLogin());
    }

    @PostMapping("/token")
    public UserTokenDto auth(@RequestBody UserAccessDto userAccessDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAccessDto.getLogin(), userAccessDto.getPassword()));

        return new UserTokenDto(jwtUtils.generateToken(authentication));
    }

}
