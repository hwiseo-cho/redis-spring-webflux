package org.example;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;
import java.util.stream.IntStream;

public class BitMap {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                jedis.setbit("request-some-202404", 100, true);
                jedis.setbit("request-some-202404", 200, true);
                jedis.setbit("request-some-202404", 300, true);

                System.out.println(jedis.getbit("request-some-202404", 100));
                System.out.println(jedis.getbit("request-some-202404", 500));

                System.out.println(jedis.bitcount("request-some-202404"));

                // bitmap vs set
                Pipeline pipelined = jedis.pipelined();
                IntStream.rangeClosed(0, 100000).forEach(i -> {
                    pipelined.sadd("request-some-202405", String.valueOf(i), "1");
                    pipelined.setbit("request-some-202406", i, true);

                    if(i== 1000) {
                        pipelined.sync();
                    }
                });
                pipelined.sync();
            }
        }
    }
}