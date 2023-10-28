package com.security.service.response;

import com.redis.model.SongDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MusicProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 123L;

    private String id;
    private String username;
    private String email;
    private List<SongDto> songs;

}
