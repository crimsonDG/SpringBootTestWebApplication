package com.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RoleDto implements Serializable {

    private Integer id;

    private String name;

}
