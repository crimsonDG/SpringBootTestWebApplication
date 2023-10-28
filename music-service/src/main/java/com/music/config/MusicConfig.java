package com.music.config;

import com.music.label.repository.LabelRepository;
import com.music.service.MusicService;
import com.music.song.init.RedisStorage;
import com.music.song.repository.SongRepository;
import com.music.song.service.SongService;
import com.music.service.RedisMusicService;
import com.redis.service.RedissonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicConfig {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private RedissonService redissonService;

    @Bean
    public RedisStorage redisStorage(){
        return new RedisStorage(songService(), redissonService);
    }

    @Bean
    public SongService songService(){
        return new SongService(modelMapper, songRepository);
    }

    @Bean
    public MusicService musicService(){
        return new MusicService(modelMapper, songService(), labelRepository);
    }

    @Bean
    public RedisMusicService redisMusicService(){
        return new RedisMusicService(redissonService);
    }
}
