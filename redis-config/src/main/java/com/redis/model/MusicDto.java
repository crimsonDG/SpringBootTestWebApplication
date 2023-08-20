package com.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;


    private String id;
    private String name;
    private String artist;
    private String album;
    private int year;
    private String duration;
}
