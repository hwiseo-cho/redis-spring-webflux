package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedSet {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // sorted set
                HashMap<String, Double> scores = new HashMap<>();
                scores.put("user1", 100.0);
                scores.put("user2", 80.0);
                scores.put("user3", 50.0);
                scores.put("user4", 70.0);
                scores.put("user5", 50.0);
                jedis.zadd("game:2:scores", scores);

                List<String> zrange = jedis.zrange("game:2:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                //List<Tuple> tuples = jedis.zrangeWithScores("game:2:scores", 0, Long.MAX_VALUE);
                //tuples.forEach(i -> System.out.println(i.getElement()+":"+i.getScore()));


                System.out.println(jedis.zcard("game:2:scores"));

                jedis.zincrby("game:2:scores", 100.0, "user5");

                List<Tuple> tuples = jedis.zrangeWithScores("game:2:scores", 0, Long.MAX_VALUE);
                tuples.forEach(i -> System.out.println(i.getElement()+":"+i.getScore()));
            }
        }
    }
}