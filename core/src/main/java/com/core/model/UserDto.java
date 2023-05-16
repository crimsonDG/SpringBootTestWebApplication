package com.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    @Size(min = 4, max = 30, message = "Name should be between 4 and 30 characters")
    private String login;
    @Size(min = 6, max = 70, message = "Name should be between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;

    private Set<RoleDto> roles = new HashSet<>();

    public void removeRole(RoleDto role) {
        this.roles.remove(role);
    }
}
