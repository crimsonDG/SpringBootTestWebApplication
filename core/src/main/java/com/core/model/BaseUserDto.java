package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {

    private String username;
    private String email;
    private String password;
    private String role;
}
