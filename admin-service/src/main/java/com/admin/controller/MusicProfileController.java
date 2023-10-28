package com.admin.controller;

import com.redis.model.SongDto;
import com.security.service.KeycloakUserService;
import com.security.service.MusicProfileService;
import com.security.service.response.MusicProfile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "Direct Access Grants"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class MusicProfileController {

    @Autowired
    private MusicProfileService musicProfileService;

    @Autowired
    private KeycloakUserService keycloakUserService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/profile/all/users")
    public Page<MusicProfile> getAllProfiles(@RequestParam(required = false) int page,
                                             @RequestParam(required = false) int size) {

        return musicProfileService.findAllUsers(keycloakUserService.findAllUsers(page, size));

    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/profile/find")
    public MusicProfile getAllUsersByName(@RequestParam String username) {
        return musicProfileService.findUserByUsername(keycloakUserService.findUserByUsername(username));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/profile/add")
    public String addSongs(String username, String songName) {
        return musicProfileService.addSongs(keycloakUserService.findUserByUsername(username), songName);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/profile/delete")
    public String deleteSongs(String username, String songName) {
        return musicProfileService.deleteSong(keycloakUserService.findUserByUsername(username), songName);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/profile/all/songs")
    public Page<SongDto> allSongs(@RequestParam(required = false) int page,
                                  @RequestParam(required = false) int size) {
        return musicProfileService.findAllSongs(page, size);
    }

}
