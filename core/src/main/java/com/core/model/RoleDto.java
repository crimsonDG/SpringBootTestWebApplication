package com.core.model;

public class RoleDto {

    private Integer id;

    private String name;

    public RoleDto() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
