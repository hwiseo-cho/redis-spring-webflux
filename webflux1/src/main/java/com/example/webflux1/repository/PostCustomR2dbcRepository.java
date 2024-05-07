package com.example.webflux1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostCustomR2dbcRepository {
    Flux<Post> findAllByUserId(Long id);
}
