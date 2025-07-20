package net.pranshhvoyages.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplatez = new RedisTemplate<>();
        redisTemplatez.setConnectionFactory(factory);

        redisTemplatez.setKeySerializer(new StringRedisSerializer());
        redisTemplatez.setValueSerializer(new StringRedisSerializer());

        return redisTemplatez;
    }
}
