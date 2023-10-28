package com.music.service;

import com.redis.model.SongDto;
import com.redis.service.RedissonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedisMusicService {

    private final RedissonService redissonService;

    public void saveSong(SongDto songDto) {
        redissonService.putData(RedissonService.STORAGE, Collections.singletonList(songDto));
    }

    public String deleteSong(SongDto songDto) {
        List<SongDto> songs = redissonService.getData(RedissonService.STORAGE);

        Optional<SongDto> songToRemove = songs.stream()
                .filter(song -> songDto.getId().equals(song.getId()))
                .findFirst();

        if (songToRemove.isPresent()) {
            List<SongDto> updatedSongs = new ArrayList<>(songs);

            if (updatedSongs.remove(songToRemove.get())) {
                songs.addAll(updatedSongs);
                redissonService.putData(songDto.getId(), updatedSongs);
                return String.format("\"%s\" removed from the main library!", songDto.getName());
            }
        }
        return String.format("\"%s\" does not exist in the main library!", songDto.getName());
    }
}
