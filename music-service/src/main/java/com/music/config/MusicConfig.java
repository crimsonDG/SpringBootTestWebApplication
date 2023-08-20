package com.music.config;

import com.music.init.RedisStorage;
import com.music.repository.MusicRepository;
import com.music.service.MusicService;
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
    private MusicRepository musicRepository;

    @Autowired
    private RedissonService redissonService;

    @Bean
    public RedisStorage redisStorage(){
        return new RedisStorage(musicService(), redissonService);
    }

    @Bean
    public MusicService musicService(){
        return new MusicService(modelMapper, musicRepository);
    }

    @Bean
    public RedisMusicService redisMusicService(){
        return new RedisMusicService(redissonService);
    }
}
