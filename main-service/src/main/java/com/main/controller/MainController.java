package com.main.controller;

import com.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/api")
@Api(value = "Endpoints of main controller")
public class MainController {

    @Autowired
    UserService userService;

    //Main page
    @GetMapping("/index")
    @ApiOperation(value = "Index page", response = String.class)
    public String index() {
        return "index";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile")
    @ApiOperation(value = "Profile page", response = String.class)
    public String profile() {
        return "profile";
    }
}
