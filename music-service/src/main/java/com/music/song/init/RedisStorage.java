package com.music.song.init;

import com.music.song.service.SongService;
import com.redis.model.SongDto;
import com.redis.service.RedissonService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisStorage {
    private final SongService songService;

    private final RedissonService redissonService;


    public RedisStorage(SongService songService, RedissonService redissonService) {
        this.songService = songService;
        this.redissonService = redissonService;
        initStorage();
    }

    private void initStorage() {
        List<SongDto> songs = songService.getSongsList();
        redissonService.putData(RedissonService.STORAGE, songs);
        redissonService.putData("eae85ea6-1be7-46df-8231-ac355074fdf4", songs.subList(7, 20));
        redissonService.putData("f156142e-4cf1-4a6d-8534-2cb71dc0f709", songs.subList(5, 12));
        redissonService.putData("6fbba4dd-b094-4e46-bdcc-e018dff356c9", songs.subList(8, 21));
    }
}
