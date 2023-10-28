package com.music.song.service;

import com.music.song.domain.SongEntity;
import com.music.song.repository.SongRepository;
import com.redis.config.CustomPage;
import com.redis.model.SongDto;
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
public class SongService {
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;

    @Cacheable(cacheNames = "song", key = "#id")
    public SongDto findSongById(String id) {
        return modelMapper.map(songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)), SongDto.class);
    }

    @Cacheable(cacheNames = "songs", key = "#name")
    public CustomPage<SongDto> findAllSongsByName(int page, int size, String name) {
        return new CustomPage<>(musicConverter(songRepository.findAllByName(PageRequest.of(page, size), name)));
    }

    @Cacheable(cacheNames = "songs", key = "#artist")
    public CustomPage<SongDto> findAllSongsByArtist(int page, int size, String artist) {
        return new CustomPage<>(musicConverter(songRepository.findAllByArtist(PageRequest.of(page, size), artist)));
    }

    @Cacheable(cacheNames = "songs", key = "#year")
    public CustomPage<SongDto> findAllSongsByYear(int page, int size, int year) {
        return new CustomPage<>(musicConverter(songRepository.findAllByYear(PageRequest.of(page, size), year)));
    }

    @Cacheable(cacheNames = "songs")
    public CustomPage<SongDto> findAllSongs(int page, int size) {
        return new CustomPage<>(musicConverter(songRepository.findAll(PageRequest.of(page, size))));
    }

    @Cacheable(cacheNames = "songs")
    public boolean saveSong(SongDto songDto) {
        songDto.setId(UUID.randomUUID().toString());
        Optional<SongEntity> musicFromDb = songRepository.findById(songDto.getId());
        if (musicFromDb.isPresent()) {
            return false;
        }

        SongEntity currentSong = modelMapper.map(songDto, SongEntity.class);
        songRepository.save(currentSong);
        return true;
    }

    @CacheEvict(cacheNames = "song", key = "#id")
    public void deleteSong(String id) {
        SongEntity song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Invalid username:" + id));
        songRepository.delete(song);
    }

    public List<SongDto> getSongsList() {
        return songRepository.findAll().stream().map(entity -> modelMapper.map(entity, SongDto.class)).toList();
    }

    private Page<SongDto> musicConverter(Page<SongEntity> musicEntities) {
        return musicEntities.map(u -> modelMapper.map(u, SongDto.class));
    }
}
