package com.admin.controller;

import com.core.model.UserDto;
import com.core.model.template.UserAccessDto;
import com.core.model.template.UserTokenDto;
import com.security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


// request mapping /admin
@RestController
@SecurityRequirements({
        @SecurityRequirement(name = "basic"),
        @SecurityRequirement(name = "Bearer Authentication")
})
public class AdminController {

    @Autowired
    private UserService userService;

    //All users page
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public Page<UserDto> getAllUsers(@RequestParam(required = false) int page,
                                     @RequestParam(required = false) int size) {
        return userService.findAllUsers(page, size);
    }

    // Sort users by ascending id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/asc")
    public Page<UserDto> getAscUsers(@RequestParam(required = false) int page,
                                     @RequestParam(required = false) int size) {
        return userService.sortAllUsersByAsc(page, size);
    }

    // Sort users by descending id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/desc")
    public Page<UserDto> getDescUsers(@RequestParam(required = false) int page,
                                      @RequestParam(required = false) int size) {
        return userService.sortAllUsersByDesc(page, size);
    }

    //Find user by login
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find")
    public UserDto findUserByString(@RequestParam String value) {
        return userService.findUserByLogin(value);
    }

    //Add user form
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/all/add")
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
    public String deleteUser(@PathVariable("id") long id) {
        String deletedUser = userService.findUserById(id).getLogin();
        userService.deleteUser(id);
        return deletedUser + " has been deleted";
    }

    @PostMapping("/token")
    public UserTokenDto auth(@RequestBody UserAccessDto userAccessDto) {
        return userService.authUser(userAccessDto);
    }
}
