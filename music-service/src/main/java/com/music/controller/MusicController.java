package com.music.controller;

import com.music.service.MusicService;
import com.music.service.RedisMusicService;
import com.redis.model.MusicDto;
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
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private RedisMusicService redisMusicService;

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/all")
    public Page<MusicDto> getAllSongs(@RequestParam(required = false) int page,
                                      @RequestParam(required = false) int size) {
        return musicService.findAllSongs(page, size);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/name")
    public Page<MusicDto> getAllSongsByName(@RequestParam(required = false) int page,
                                            @RequestParam(required = false) int size,
                                            @RequestParam String name) {
        return musicService.findAllSongsByName(page, size, name);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/artist")
    public Page<MusicDto> getAllSongsByArtist(@RequestParam(required = false) int page,
                                              @RequestParam(required = false) int size,
                                              @RequestParam String artist) {
        return musicService.findAllSongsByArtist(page, size, artist);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/year")
    public Page<MusicDto> getAllSongsByYear(@RequestParam(required = false) int page,
                                            @RequestParam(required = false) int size,
                                            @RequestParam int year) {
        return musicService.findAllSongsByYear(page, size, year);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/songs/add")
    public MusicDto setSong(@RequestBody MusicDto musicDto) {
        if (!musicService.saveSong(musicDto)) {
            System.out.println("ERROR");
            return null;
        }
        MusicDto response = musicService.findSongById(musicDto.getId());
        redisMusicService.saveSong(response);
        return response;
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/songs/delete")
    public String deleteSong(@RequestParam("username") String name) {
        MusicDto response = (MusicDto) musicService.findAllSongsByName(0, 1, name).stream().toList();
        musicService.deleteSong(response.getId());
        return redisMusicService.deleteSong(response);
    }

}
