package com.security.service;

import com.core.model.KeycloakEntityDto;
import com.redis.config.CustomPage;
import com.redis.model.SongDto;
import com.redis.service.RedissonService;
import com.security.service.response.MusicProfile;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicProfileService {

    private final RedissonService redissonService;

    private final ModelMapper modelMapper;


    public MusicProfile findUserById(KeycloakEntityDto user) {
        MusicProfile currentUser = modelMapper.map(user, MusicProfile.class);
        currentUser.setSongs(redissonService.getData(currentUser.getId()));
        return currentUser;
    }

    public MusicProfile findUserByUsername(KeycloakEntityDto user) {
        MusicProfile currentUser = modelMapper.map(user, MusicProfile.class);
        currentUser.setSongs(redissonService.getData(currentUser.getUsername()));
        return currentUser;
    }

    public CustomPage<MusicProfile> findAllUsers(CustomPage<KeycloakEntityDto> users) {
        return new CustomPage<>(addField(userConverter(users)));
    }

    public String addSongs(KeycloakEntityDto user, String songName) {
        List<SongDto> songs = redissonService.getData(RedissonService.STORAGE);
        MusicProfile currentUser = modelMapper.map(user, MusicProfile.class);

        Optional<SongDto> existingSong = songs.stream()
                .filter(musicDto -> songName.equals(musicDto.getName()))
                .findFirst();

        if (existingSong.isPresent()) {
            List<SongDto> updatedSongs = new ArrayList<>(currentUser.getSongs());

            if (!updatedSongs.contains(existingSong.get())) {
                updatedSongs.add(existingSong.get());
                currentUser.setSongs(updatedSongs);
                redissonService.putData(currentUser.getId(), updatedSongs);
                return String.format("%s gets \"%s\" to his library!", currentUser.getUsername(), songName);
            } else {
                return String.format("\"%s\" is already in %s's library!", songName, currentUser.getUsername());
            }
        } else {
            return String.format("\"%s\" does not exist in the main library!", songName);
        }
    }

    public String deleteSong(KeycloakEntityDto user, String songName){
        List<SongDto> songs = redissonService.getData(RedissonService.STORAGE);
        MusicProfile currentUser = modelMapper.map(user, MusicProfile.class);

        Optional<SongDto> songToRemove = songs.stream()
                .filter(musicDto -> songName.equals(musicDto.getName()))
                .findFirst();

        if (songToRemove.isPresent()) {
            List<SongDto> updatedSongs = new ArrayList<>(currentUser.getSongs());

            if (updatedSongs.remove(songToRemove.get())) {
                currentUser.setSongs(updatedSongs);
                redissonService.putData(currentUser.getId(), updatedSongs);
                return String.format("Removed \"%s\" from %s's library!", currentUser.getUsername(), songName);
            } else {
                return String.format("\"%s\" was not found in the library!", songName);
            }
        } else {
            return String.format("\"%s\" does not exist in the %s's library!", songName, currentUser.getUsername());
        }
    }

    public CustomPage<SongDto> findAllSongs(int page, int size) {
        List<SongDto> songs = redissonService.getData(RedissonService.STORAGE);
        return new CustomPage<>(songs, page, size, songs.size());
    }

    private Page<MusicProfile> userConverter(Page<KeycloakEntityDto> usersEntities) {
        return usersEntities.map(u -> modelMapper.map(u, MusicProfile.class));
    }


    private Page<MusicProfile> addField(Page<MusicProfile> user) {
        List<MusicProfile> users = user.stream().toList();
        for (MusicProfile userDto : users) {
            userDto.setSongs(redissonService.getData(userDto.getId()));
        }
        return new PageImpl<>(users);
    }

}
