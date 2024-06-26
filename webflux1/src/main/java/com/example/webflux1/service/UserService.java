package com.example.webflux1.service;

import com.example.webflux1.repository.User;
import com.example.webflux1.repository.UserR2dbcRepository;
import com.example.webflux1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    //private final UserRepository userRepository;
    private final UserR2dbcRepository userRepository;

    private final ReactiveRedisTemplate<String, User> redisTemplate;

    public Mono<User> create(String name, String email) {
        return userRepository.save(User.builder().name(name).email(email).build());
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    private String getUserCacheKey(Long id) {
        return "users:%d".formatted(id);
    }

    public Mono<User> findById(Long id) {
        return redisTemplate.opsForValue()
                .get(getUserCacheKey(id))
                .switchIfEmpty(
                        userRepository.findById(id)
                                .flatMap(u -> redisTemplate.opsForValue()
                                        .set(getUserCacheKey(id), u)
                                        .then(Mono.just(u)))
                );
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    public Mono<Void> deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    public Mono<User> update(Long id, String name, String email) {
        return userRepository.findById(id)
                .flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userRepository.save(u);
                });
    }

}
