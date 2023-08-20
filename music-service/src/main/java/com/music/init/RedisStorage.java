package com.music.init;

import com.music.service.MusicService;
import com.redis.model.MusicDto;
import com.redis.service.RedissonService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisStorage {
    private final MusicService musicService;

    private final RedissonService redissonService;


    public RedisStorage(MusicService musicService, RedissonService redissonService) {
        this.musicService = musicService;
        this.redissonService = redissonService;
        initStorage();
    }

    private void initStorage() {
        List<MusicDto> songs = musicService.getSongsList();
        redissonService.putData(RedissonService.STORAGE, songs);
        redissonService.putData("eae85ea6-1be7-46df-8231-ac355074fdf4", songs.subList(7, 20));
        redissonService.putData("f156142e-4cf1-4a6d-8534-2cb71dc0f709", songs.subList(5, 12));
        redissonService.putData("6fbba4dd-b094-4e46-bdcc-e018dff356c9", songs.subList(8, 21));
    }
}
