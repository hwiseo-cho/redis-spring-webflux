package com.example.webflux1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RedisConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final ReactiveRedisTemplate<String ,String> redisTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        redisTemplate.opsForValue().get("1")
                .doOnSuccess(i -> log.info("redis success"))
                .doOnError(e -> log.error("redis error"))
                .subscribe();

        /*redisTemplate.opsForList().leftPush("Lists1", "hello").subscribe();
        redisTemplate.opsForValue().set("sample", "sdfdsf", Duration.ofSeconds(10)).subscribe();*/
    }
}
