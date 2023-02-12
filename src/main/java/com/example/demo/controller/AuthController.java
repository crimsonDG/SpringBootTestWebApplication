package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String register(User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "The login already exists");
            result.getModel();
            return "registration";
        }
        return "redirect:/login";
    }

}
