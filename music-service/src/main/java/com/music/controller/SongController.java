package com.music.controller;

import com.music.song.service.SongService;
import com.music.service.RedisMusicService;
import com.redis.model.SongDto;
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
public class SongController {
    @Autowired
    private SongService songService;

    @Autowired
    private RedisMusicService redisMusicService;

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/all")
    public Page<SongDto> getAllSongs(@RequestParam(required = false) int page,
                                     @RequestParam(required = false) int size) {
        return songService.findAllSongs(page, size);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/name")
    public Page<SongDto> getAllSongsByName(@RequestParam(required = false) int page,
                                           @RequestParam(required = false) int size,
                                           @RequestParam String name) {
        return songService.findAllSongsByName(page, size, name);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/artist")
    public Page<SongDto> getAllSongsByArtist(@RequestParam(required = false) int page,
                                             @RequestParam(required = false) int size,
                                             @RequestParam String artist) {
        return songService.findAllSongsByArtist(page, size, artist);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/songs/year")
    public Page<SongDto> getAllSongsByYear(@RequestParam(required = false) int page,
                                           @RequestParam(required = false) int size,
                                           @RequestParam int year) {
        return songService.findAllSongsByYear(page, size, year);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/songs/add")
    public SongDto setSong(@RequestBody SongDto songDto) {
        if (!songService.saveSong(songDto)) {
            System.out.println("ERROR");
            return null;
        }
        SongDto response = songService.findSongById(songDto.getId());
        redisMusicService.saveSong(response);
        return response;
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/songs/delete")
    public String deleteSong(@RequestParam("username") String name) {
        SongDto response = (SongDto) songService.findAllSongsByName(0, 1, name).stream().toList();
        songService.deleteSong(response.getId());
        return redisMusicService.deleteSong(response);
    }

}
