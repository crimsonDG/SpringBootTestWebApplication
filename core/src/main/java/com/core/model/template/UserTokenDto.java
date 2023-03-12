package com.core.model.template;

public class UserTokenDto {

    private String jwt;

    public UserTokenDto(String jwt){
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
