package com.core.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Long id;
    @Size(min = 4, max = 30, message = "Name should be between 4 and 30 characters")
    private String login;
    @Size(min = 6, max = 70, message = "Name should be between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;

    public UserDto() {
    }

    public UserDto(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    private Set<RoleDto> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public void removeRole(RoleDto role) {
        this.roles.remove(role);
    }
}