package com.auth.controller;

import com.core.model.template.UserAccessDto;
import com.core.model.UserDto;
import com.core.model.template.UserTokenDto;
import com.security.service.UserService;
import com.security.token.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(value = "Endpoints of auth controller")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registration")
    @ApiOperation(value = "Register user", response = UserDto.class)
    public UserDto registerUser(@RequestBody UserDto userDto) {
        if (!userService.saveUser(userDto)) {
            System.out.println("The login already exists");
            return null;
        }
        return userService.findUserByLogin(userDto.getLogin());
    }

    @PostMapping("/token")
    @ApiOperation(value = "Get token for user", response = UserTokenDto.class)
    public UserTokenDto auth(@RequestBody UserAccessDto userAccessDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAccessDto.getLogin(), userAccessDto.getPassword()));

        return new UserTokenDto(jwtUtils.generateToken(authentication));
    }

}
