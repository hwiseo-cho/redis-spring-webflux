package com.example.redis;

import com.example.redis.entity.User;
import com.example.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class RedisApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.save(
				User.builder()
						.name("asdf")
						.email("sdlfsdls@naver.com")
						.build()
		);userRepository.save(
				User.builder()
						.name("wqe")
						.email("123321@naver.com")
						.build()
		);userRepository.save(
				User.builder()
						.name("xvccxxvc")
						.email("v34@naver.com")
						.build()
		);userRepository.save(
				User.builder()
						.name("reere")
						.email("xqdxwq@naver.com")
						.build()
		);
	}
}
