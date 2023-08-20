package com.music.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class MusicEntity {

    @Id
    private String id;

    private String name;

    private String artist;

    private String album;

    private int year;

    private String duration;

}
