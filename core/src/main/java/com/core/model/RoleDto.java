package com.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private Integer id;

    private String name;

}
