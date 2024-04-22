package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Hash {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // Hash

                jedis.hset("users:2:info", "name", "grep2");
                var userInfo = new HashMap<String, String>();

                userInfo.put("email","sdlfkkjflks@naver.com");
                userInfo.put("phone", "20020302203");

                jedis.hset("users:2:info", userInfo);

                // hdel
                jedis.hdel("users:2:info", "phone");

                // get
                System.out.println(jedis.hget("users:2:info", "email"));
                Map<String, String> stringStringMap = jedis.hgetAll("users:2:info");
                System.out.println(stringStringMap.toString());

                // hincr
                jedis.hincrBy("users:2:info", "visit", 1);
            }
        }
    }
}