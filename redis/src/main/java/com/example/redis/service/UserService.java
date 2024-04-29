package com.example.redis.service;

import com.example.redis.config.CacheConfig;
import com.example.redis.entity.RedisHashUser;
import com.example.redis.entity.User;
import com.example.redis.repository.RedisHashUserRepository;
import com.example.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisHashUserRepository redisHashUserRepository;

    public User getUser(final Long id) {
        String key = "users:%d".formatted(id);
        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if(cachedUser != null) {
            return (User) cachedUser;
        }
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }

    public RedisHashUser getUser2(final Long id) {
        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createTime(user.getCreateTime())
                    .updateTime(user.getUpdateTime())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CacheConfig.CACHE1, key = "'user:' + #id")
    public User getUser3(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
