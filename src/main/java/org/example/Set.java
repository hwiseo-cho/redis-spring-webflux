package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Set {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // Set
                jedis.sadd("users:500:follow", "100", "200", "300");
                jedis.srem("users:500:follow", "100");

                jedis.smembers("users:500:follow").forEach(System.out::println);

                System.out.println(jedis.sismember("users:500:follow", "200"));
                System.out.println(jedis.sismember("users:500:follow", "4s00"));

                System.out.println(jedis.scard("users:500:follow"));

                System.out.println(jedis.sinter("users:500:follow"));
                java.util.Set<String> sinter = jedis.sinter("users:500:follow");
            }
        }
    }
}