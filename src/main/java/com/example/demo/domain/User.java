package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Login is mandatory")
    @Size(min = 4, max = 30, message = "Name should be between 4 and 30 characters")
    private String login;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 70, message = "Name should be between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;

    public User() {
    }
    public User(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

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

}
