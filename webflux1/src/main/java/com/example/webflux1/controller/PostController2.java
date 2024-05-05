package com.example.webflux1.controller;

import com.example.webflux1.dto.PostCreateRequest;
import com.example.webflux1.dto.PostResponse2;
import com.example.webflux1.service.PostService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostController2 {

    private final PostService2 postService2;

    @PostMapping
    public Mono<PostResponse2> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return postService2.create(postCreateRequest.getUserId(), postCreateRequest.getTitle(), postCreateRequest.getContent())
                .map(PostResponse2::of);
    }

    @GetMapping
    public Flux<PostResponse2> findAllPost() {
        return postService2.findAll()
                .map(PostResponse2::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponse2>> findPost(@PathVariable Long id) {
        return postService2.findById(id)
                .map(i -> ResponseEntity.ok().body(PostResponse2.of(i)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<PostResponse2>> deletePost(@PathVariable Long id) {
        return postService2.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
