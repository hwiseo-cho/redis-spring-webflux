package com.example.webflux1.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PostCustomR2dbcRepositoryImpl implements PostCustomR2dbcRepository{

    private final DatabaseClient databaseClient;

    @Override
    public Flux<Post> findAllByUserId(Long id) {
        var sql = "SELECT ... FROM posts p LEFT JOIN user u ON p.user_id = u.id WHERE p.user_id = :userId";
        return databaseClient.sql(sql)
                .bind("userId", id)
                .fetch()
                .all()
                .map(row -> Post.builder().build());
    }
}
