package com.redis.service;

import com.redis.model.SongDto;
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

    public void putData(String key, List<SongDto> value) {
        RMap<String, List<SongDto>> map = redissonClient.getMap(key);
        map.put(key, value);
    }

    public List<SongDto> getData(String key) {
        RMap<String, List<SongDto>> map = redissonClient.getMap(key);
        return map.get(key);
    }

    public void deleteData(String key) {
        RMap<String, List<SongDto>> map = redissonClient.getMap(key);
        map.delete();
    }
}
