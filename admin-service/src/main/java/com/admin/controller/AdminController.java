package com.admin.controller;

import com.core.model.UserDto;
import com.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// request mapping /admin
@RestController
@Api(value = "Endpoints of admin controller")
public class AdminController {

    @Autowired
    private UserService userService;

    //All users page
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    @ApiOperation(value = "All users", response = UserDto[].class)
    public List<UserDto> getAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    // Sort users by ascending id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/asc")
    @ApiOperation(value = "Asc users", response = UserDto[].class)
    public List<UserDto> getAscUsers(Pageable pageable) {
        return userService.sortAllUsersByAsc(pageable);
    }

    // Sort users by descending id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/desc")
    @ApiOperation(value = "Desc users", response = UserDto[].class)
    public List<UserDto> getDescUsers(Pageable pageable) {
        return userService.sortAllUsersByDesc(pageable);
    }

    //Find user by login
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find")
    @ApiOperation(value = "Find current user", response = UserDto.class)
    public UserDto findUserByString(@RequestParam String value) {
        return userService.findUserByLogin(value);
    }

    //Add user form
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/all/add")
    @ApiOperation(value = "Add user", response = UserDto.class)
    public UserDto addUser(@RequestBody UserDto userDto) {
        if (!userService.saveUser(userDto)) {
            System.out.println("The login already exists");
            return null;
        }
        return userService.findUserByLogin(userDto.getLogin());
    }

    //Update user form
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/all/update/{id}")
    @ApiOperation(value = "Update user", response = UserDto.class)
    public UserDto updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        if (!userService.updateUser(userDto, id)) {
            System.out.println("The login already exists");
            return null;
        }
        return userService.findUserByLogin(userDto.getLogin());
    }

    //Delete user
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete user", response = String.class)
    public String deleteUser(@PathVariable("id") long id) {
        String deletedUser = userService.findUserById(id).getLogin();
        userService.deleteUser(id);
        return deletedUser + " has been deleted";
    }
}
