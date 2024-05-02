package com.example.webflux1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @GetMapping("/sample/hello")
    public Mono<String> getHello() {

        // reactor
        // publisher <--> subscriber

        return Mono.just("hello mono with webflux");
    }
}
