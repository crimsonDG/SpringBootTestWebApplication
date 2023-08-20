package com.music.service;

import com.music.domain.MusicEntity;
import com.music.repository.MusicRepository;
import com.redis.config.CustomPage;
import com.redis.model.MusicDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "songCache")
public class MusicService {
    private final ModelMapper modelMapper;
    private final MusicRepository musicRepository;

    @Cacheable(cacheNames = "song", key = "#id")
    public MusicDto findSongById(String id) {
        return modelMapper.map(musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)), MusicDto.class);
    }

    @Cacheable(cacheNames = "songs", key = "#name")
    public CustomPage<MusicDto> findAllSongsByName(int page, int size, String name) {
        return new CustomPage<>(musicConverter(musicRepository.findAllByName(PageRequest.of(page, size), name)));
    }

    @Cacheable(cacheNames = "songs", key = "#artist")
    public CustomPage<MusicDto> findAllSongsByArtist(int page, int size, String artist) {
        return new CustomPage<>(musicConverter(musicRepository.findAllByArtist(PageRequest.of(page, size), artist)));
    }

    @Cacheable(cacheNames = "songs", key = "#year")
    public CustomPage<MusicDto> findAllSongsByYear(int page, int size, int year) {
        return new CustomPage<>(musicConverter(musicRepository.findAllByYear(PageRequest.of(page, size), year)));
    }

    @Cacheable(cacheNames = "songs")
    public CustomPage<MusicDto> findAllSongs(int page, int size) {
        return new CustomPage<>(musicConverter(musicRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "songs")
    public boolean saveSong(MusicDto musicDto) {
        musicDto.setId(UUID.randomUUID().toString());
        Optional<MusicEntity> musicFromDb = musicRepository.findById(musicDto.getId());
        if (musicFromDb.isPresent()) {
            return false;
        }

        MusicEntity currentSong = modelMapper.map(musicDto, MusicEntity.class);
        musicRepository.save(currentSong);
        return true;
    }

    @CacheEvict(cacheNames = "song", key = "#id")
    public void deleteSong(String id) {
        MusicEntity song = musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Invalid username:" + id));
        musicRepository.delete(song);
    }

    public List<MusicDto> getSongsList() {
        return musicRepository.findAll().stream().map(entity -> modelMapper.map(entity, MusicDto.class)).toList();
    }

    private Page<MusicDto> musicConverter(Page<MusicEntity> musicEntities) {
        return musicEntities.map(u -> modelMapper.map(u, MusicDto.class));
    }
}
