package org.example;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geo {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // geo
                jedis.geoadd("stores:geo", 126.9524, 37.4816, "some1");
                jedis.geoadd("stores:geo", 126.9524423, 37.4816234, "some2");

                // dist
                Double geodist = jedis.geodist("stores:geo", "some1", "some2");
                System.out.println(geodist);

                // search
                /*List<GeoRadiusResponse> geosearch = jedis.geosearch("stores:geo", new GeoCoordinate(126.1233, 37.3212),
                        1000000,
                        GeoUnit.M);

                geosearch.forEach(g -> System.out.println(
                        g.getMemberByString()+":"+g.getCoordinate().getLatitude()+":"+g.getCoordinate().getLongitude()
                ));*/

                List<GeoRadiusResponse> geosearch1 = jedis.geosearch("stores:geo", new GeoSearchParam()
                        .fromLonLat(new GeoCoordinate(126.1233, 37.3212))
                        .byRadius(1000000, GeoUnit.M)
                        .withCoord());

                geosearch1.forEach(g -> System.out.println(
                        g.getMemberByString()+":"+g.getCoordinate().getLatitude()+":"+g.getCoordinate().getLongitude()
                ));

                jedis.unlink("stores:geo");
            }
        }
    }
}