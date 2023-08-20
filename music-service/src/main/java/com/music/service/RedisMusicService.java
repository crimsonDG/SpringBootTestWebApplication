package com.music.service;

import com.redis.model.MusicDto;
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

    public void saveSong(MusicDto musicDto) {
        redissonService.putData(RedissonService.STORAGE, Collections.singletonList(musicDto));
    }

    public String deleteSong(MusicDto musicDto) {
        List<MusicDto> songs = redissonService.getData(RedissonService.STORAGE);

        Optional<MusicDto> songToRemove = songs.stream()
                .filter(song -> musicDto.getId().equals(song.getId()))
                .findFirst();

        if (songToRemove.isPresent()) {
            List<MusicDto> updatedSongs = new ArrayList<>(songs);

            if (updatedSongs.remove(songToRemove.get())) {
                songs.addAll(updatedSongs);
                redissonService.putData(musicDto.getId(), updatedSongs);
                return String.format("\"%s\" removed from the main library!", musicDto.getName());
            }
        }
        return String.format("\"%s\" does not exist in the main library!", musicDto.getName());
    }
}
