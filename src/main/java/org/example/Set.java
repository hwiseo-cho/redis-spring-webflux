package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Set {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // Set

            }
        }
    }
}