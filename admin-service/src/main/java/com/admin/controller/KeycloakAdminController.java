package com.admin.controller;

import com.core.model.BaseUserDto;
import com.core.model.KeycloakEntityDto;
import com.core.model.template.UserAccess;
import com.core.model.template.UserToken;
import com.security.service.KeycloakUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "Direct Access Grants"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class KeycloakAdminController {

    @Autowired
    private KeycloakUserService keycloakUserService;


    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/keycloak/add")
    public KeycloakEntityDto createKeycloakUser(@RequestBody BaseUserDto baseUserDto) {
        if (!keycloakUserService.saveUser(baseUserDto)) {
            System.out.println("ERROR");
            return null;
        }
        return keycloakUserService.findUserByUsername(baseUserDto.getUsername());
    }


    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/all")
    public Page<KeycloakEntityDto> getAllUsers(@RequestParam(required = false) int page,
                                               @RequestParam(required = false) int size) {
        return keycloakUserService.findAllUsers(page, size);
    }

    // Sort users by ascending id
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/asc")
    public Page<KeycloakEntityDto> getAscUsers(@RequestParam(required = false) int page,
                                               @RequestParam(required = false) int size) {
        return keycloakUserService.sortAllUsersByAsc(page, size);
    }

    // Sort users by descending id
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/desc")
    public Page<KeycloakEntityDto> getDescUsers(@RequestParam(required = false) int page,
                                                @RequestParam(required = false) int size) {
        return keycloakUserService.sortAllUsersByDesc(page, size);
    }

    //Find user by login
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/find")
    public KeycloakEntityDto findUserByString(@RequestParam String value) {
        return keycloakUserService.findUserByUsername(value);
    }


    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PutMapping("/keycloak/update/{username}")
    public KeycloakEntityDto updateUser(@PathVariable("username") String username, @RequestBody BaseUserDto baseUserDto) {
        if (!keycloakUserService.updateUser(baseUserDto, keycloakUserService.findUserByUsername(username).getId())) {
            System.out.println("ERROR");
            return null;
        }
        return keycloakUserService.findUserByUsername(baseUserDto.getUsername());
    }


    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/keycloak/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        keycloakUserService.deleteUser(keycloakUserService.findUserByUsername(username).getId());
        return username + " has been deleted";
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
