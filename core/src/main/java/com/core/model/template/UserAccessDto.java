package com.core.model.template;


public class UserAccessDto {

    private String login;
    private String password;

    public UserAccessDto(String login, String password){
        this.login = login;
        this.password = password;
    }

    public UserAccessDto(){
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
}
