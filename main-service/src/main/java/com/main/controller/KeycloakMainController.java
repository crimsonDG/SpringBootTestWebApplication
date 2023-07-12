package com.main.controller;

import com.core.model.template.UserAccess;
import com.core.model.template.UserToken;
import com.security.service.KeycloakUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "Direct Access Grants"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class KeycloakMainController {

    @Autowired
    KeycloakUserService keycloakUserService;

    //Main page
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/index")
    public String index() {
        return "index";
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/profile")
    public String profile() {
        return "profile";
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/token")
    public UserToken auth(@RequestBody UserAccess userAccess) {
        return keycloakUserService.authUser(userAccess);
    }
}
