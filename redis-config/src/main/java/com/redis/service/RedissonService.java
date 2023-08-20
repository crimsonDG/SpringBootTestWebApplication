package com.redis.service;

import com.redis.model.MusicDto;
import lombok.AllArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RedissonService {

    private final RedissonClient redissonClient;

    public static final String STORAGE = "main";

    public void putData(String key, List<MusicDto> value) {
        RMap<String, List<MusicDto>> map = redissonClient.getMap(key);
        map.put(key, value);
    }

    public List<MusicDto> getData(String key) {
        RMap<String, List<MusicDto>> map = redissonClient.getMap(key);
        return map.get(key);
    }

    public void deleteData(String key) {
        RMap<String, List<MusicDto>> map = redissonClient.getMap(key);
        map.delete();
    }
}
