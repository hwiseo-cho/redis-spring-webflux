package com.example.redis.controller;

import com.example.redis.entity.RedisHashUser;
import com.example.redis.repository.UserRepository;
import com.example.redis.entity.User;
import com.example.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/redisUsers/{id}")
    public RedisHashUser getUser2(@PathVariable Long id) {
        return userService.getUser2(id);
    }

    @GetMapping("/cacheUsers/{id}")
    public User getUser3(@PathVariable Long id) {
        return userService.getUser3(id);
    }

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id) {

        /*try (Jedis jedis = jedisPool.getResource()) {
            var userEmailRedisKey = "users:%d:email".formatted(id);
            // 1. reqeust to cache
            String userEmail = jedis.get(userEmailRedisKey);
            if(userEmail != null) {
                return userEmail;
            }

            // 2. else db
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();

            // 3. cache
            jedis.setex(userEmailRedisKey, 30, userEmail);
            return userEmail;
        }*/
        return "";
    }
}
