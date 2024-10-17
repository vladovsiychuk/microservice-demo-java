package com.microservice.backend_for_frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
  @Bean
  ReactiveRedisOperations<String, PostAggregate> redisOperations(ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<PostAggregate> serializer = new Jackson2JsonRedisSerializer<>(PostAggregate.class);

    RedisSerializationContext.RedisSerializationContextBuilder<String, PostAggregate> builder =
        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

    RedisSerializationContext<String, PostAggregate> context = builder.value(serializer).build();

    return new ReactiveRedisTemplate<>(factory, context);
  }

}
