package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

public class List {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // List
                // 1. stack
                /*jedis.rpush("stack1", "aaaa");
                jedis.rpush("stack1", "bbbb");
                jedis.rpush("stack1", "cccc");

                java.util.List<String> stack1 = jedis.lrange("stack1", 0, -1);
                // stack1.forEach(System.out::println);

                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));

                // 2. queue
                jedis.rpush("queue2", "zzzz");
                jedis.rpush("queue2", "xxxx");
                jedis.rpush("queue2", "cccc");

                System.out.println(jedis.lpop("queue2"));
                System.out.println(jedis.lpop("queue2"));
                System.out.println(jedis.lpop("queue2"));*/

                // 3. block brpop, blpop
                // 정한 시간 만큼 기다림
                java.util.List<String> blpop = jedis.blpop(10, "queue-blocking");
                if(blpop != null) {
                    blpop.forEach(System.out::println);
                }
                /*while (true) {
                    java.util.List<String> blpop = jedis.blpop(10, "queue-blocking");
                    if(blpop != null) {
                        blpop.forEach(System.out::println);
                    }
                }*/
            }
        }
    }
}