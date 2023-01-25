package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserDatabaseController {

    @Autowired
    private UserRepository userRepository;


    //All users page
    @GetMapping(path = "/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "all";
    }

    // Sort users by ascending id
    @GetMapping("/asc")
    public String getAscUsers(Model model) {
        model.addAttribute("users", userRepository.ascSorted());
        return "all";
    }

    // Sort users by descending id
    @GetMapping("/desc")
    public String getDescUsers(Model model) {
        model.addAttribute("users", userRepository.descSorted());
        return "all";
    }

    //Default users position
    @GetMapping("/default")
    public String getDefaultUsers(Model model) {
        return "redirect:/all";
    }

    //Find user by login
    @PostMapping("/find")
    public String findUserByString(@RequestParam String value, Model model) {
        model.addAttribute("users", userRepository.findByLogin(value));
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
        userRepository.save(user);
        return "redirect:/all";
    }

    //Update user page
    @GetMapping("/all/edit/{id}")
    public String showUpdateUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
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
        userRepository.save(user);
        return "redirect:/all";
    }

    //Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/all";
    }


}
