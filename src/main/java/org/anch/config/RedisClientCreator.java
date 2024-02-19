package org.anch.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisClientCreator implements AutoCloseable {

    private static RedisClientCreator instance;

    private final RedisClient redisClient;

    private RedisClientCreator() {
        redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            System.out.println("\nConnected to Redis\n");
        }
    }

    public static RedisClient prepareRedisClient() {
        if (instance == null) {
            instance = new RedisClientCreator();
        }
        return instance.redisClient;
    }

    @Override
    public void close() {
        instance.close();
    }

}
