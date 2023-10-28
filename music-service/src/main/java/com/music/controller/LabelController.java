package com.music.controller;

import com.music.label.model.LabelDto;
import com.music.service.MusicService;
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
public class LabelController {
    @Autowired
    private MusicService musicService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/labels/all")
    public Page<LabelDto> getAllLabels(@RequestParam(required = false) int page,
                                       @RequestParam(required = false) int size) {
        return musicService.findAllLabels(page, size);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/labels/name")
    public LabelDto getAllLabelByName(@RequestParam String name) {
        return musicService.findLabelByName(name);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/labels/save")
    public LabelDto saveLabel(@RequestParam String labelName) {
        LabelDto labelDto = new LabelDto();
        labelDto.setId(labelName);
        labelDto.setName(labelName);
        if (!musicService.saveLabel(labelDto)) {
            System.out.println("ERROR");
            return null;
        }
        return labelDto;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/labels/delete")
    public String deleteLabel(@RequestParam("name") String name) {
        musicService.deleteLabel(musicService.findLabelByName(name).getId());
        return name + " has been deleted";
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PutMapping("/labels/element/add")
    public String addSongs(String labelName, String songId) {
        return musicService.addSongs(musicService.findLabelByName(labelName), songId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PutMapping("/labels/element/delete")
    public String deleteSongs(String labelName, String songId) {
        return musicService.deleteSong(musicService.findLabelByName(labelName), songId);
    }
}
