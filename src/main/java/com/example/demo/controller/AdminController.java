package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    //All users page
    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "all";
    }

    // Sort users by ascending id
    @GetMapping("/asc")
    public String getAscUsers(Model model) {
        model.addAttribute("users", userService.sortAllUsersByAsc());
        return "all";
    }

    // Sort users by descending id
    @GetMapping("/desc")
    public String getDescUsers(Model model) {
        model.addAttribute("users", userService.sortAllUsersByDesc());
        return "all";
    }

    //Find user by login
    @PostMapping("/find")
    public String findUserByString(@RequestParam String value, Model model) {
        model.addAttribute("users", userService.findUserByLogin(value));
        return "all";
    }

    //Add user page
    @GetMapping(path = "/all/add")
    public String showAddUser(User user) {
        return "add";
    }

    //Add user form
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add";
        }
        if (!userService.saveUser(user, false)){
            model.addAttribute("usernameError", "The login already exists");
            return "add";
        }
        return "redirect:/all";
    }

    //Update user page
    @GetMapping("/all/edit/{id}")
    public String showUpdateUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "update";
    }

    //Update user form
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update";
        }

        if (!userService.saveUser(user, true)){
            model.addAttribute("usernameError", "The login already exists");
            return "update";
        }
        return "redirect:/all";
    }

    //Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/all";
    }
}
