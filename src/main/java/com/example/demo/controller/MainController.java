package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    //Main page
    @GetMapping(path = "/index")
    public String index() {
        return "index";
    }

}
