package com.auth.controller;

import com.auth.rabbitmq.AuthUserSender;
import com.auth.rabbitmq.RegisteredUserSender;
import com.core.model.BaseUserDto;
import com.core.model.KeycloakEntityDto;
import com.core.model.template.UserAccess;
import com.core.model.template.UserToken;
import com.security.service.KeycloakUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@RestController
public class KeycloakAuthController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @Autowired
    private AuthUserSender authUserSender;

    @Autowired
    private RegisteredUserSender registeredUserSender;

    @Autowired
    private RestTemplate restTemplate;

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/keycloak/registration")
    public KeycloakEntityDto registerKeycloakUser(@RequestBody BaseUserDto baseUserDto) throws IOException, TimeoutException {
        if (!keycloakUserService.saveUser(baseUserDto)) {
            System.out.println("The user already exists");
            return null;
        }
        KeycloakEntityDto currentUser = keycloakUserService.findUserByUsername(baseUserDto.getUsername());
        registeredUserSender.sendRegisteredUser(currentUser);

        return keycloakUserService.findUserByUsername(baseUserDto.getUsername());
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/keycloak/token")
    public UserToken auth(@RequestBody UserAccess userAccess) throws IOException, TimeoutException {
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //Body
        MultiValueMap<String, String> body = userAccess.toMultiValueMap();

        KeycloakEntityDto currentUser = keycloakUserService.findUserByUsername(userAccess.getUsername());
        authUserSender.sendAuthUser(currentUser);

        return restTemplate.postForEntity(keycloakUserService.getTokenUrl(), new HttpEntity<>(body, headers), UserToken.class).getBody();
    }

}
