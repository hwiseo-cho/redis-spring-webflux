package com.example.webflux1.config;

import com.example.webflux1.repository.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

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

    @Bean
    public ReactiveRedisTemplate<String, User> redisUserTemplate(ReactiveRedisConnectionFactory redisConnectionFactory) {
        var objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

        Jackson2JsonRedisSerializer<User> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, User.class);

        RedisSerializationContext<String, User> serializationContext = RedisSerializationContext
                .<String, User>newSerializationContext()
                .key(RedisSerializer.string())
                .value(jsonRedisSerializer)
                .hashKey(RedisSerializer.string())
                .hashValue(jsonRedisSerializer)
                .build();

        return new ReactiveRedisTemplate<>(redisConnectionFactory, serializationContext);
    }
}
