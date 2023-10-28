package com.music.label.model;

import com.redis.model.SongDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234L;

    private String id;
    private String name;
    private List<SongDto> songs;
}
