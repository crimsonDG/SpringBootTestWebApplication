package com.admin.controller;

import com.admin.excel.ExcelReader;
import com.admin.excel.ExcelWriter;
import com.core.model.KeycloakEntityDto;
import com.security.service.KeycloakUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "Direct Access Grants"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class ExcelController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/keycloak/excel/download")
    public ResponseEntity<Resource> downloadFile() {
        InputStreamResource file = new InputStreamResource(ExcelWriter.usersToExcel(keycloakUserService.getUserList()));
        String filename = "users.xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/keycloak/excel/upload")
    public List<KeycloakEntityDto> uploadFile(@RequestParam MultipartFile file) throws Exception {
        return ExcelReader.excelToObject(file);
    }
}
