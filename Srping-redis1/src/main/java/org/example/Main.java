package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {

                jedis.set("users:2:email", "gnltj2@naver.com");
                jedis.set("users:2:name", "sdlfsjkdl");
                jedis.set("users:2:age", "123");

                var userEmail = jedis.get("users:2:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:2:email", "users:2:name", "users:2:age");
                userInfo.forEach(System.out::println);

                long counter = jedis.incr("counter");
                System.out.println("counter: "+counter);

                counter = jedis.incrBy("counter", 10L);
                System.out.println("counter: "+counter);

                counter = jedis.decr("counter");
                System.out.println("counter: "+counter);

                counter = jedis.decrBy("counter", 10L);
                System.out.println("counter: "+counter);

                Pipeline pipelined = jedis.pipelined();
                pipelined.set("users:3:email", "gnltj@naver.com");
                pipelined.set("users:3:name", "asdas");
                pipelined.set("users:3:age", "23");
                List<Object> objects = pipelined.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));
            }
        }
    }
}