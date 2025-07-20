package net.pranshhvoyages.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            assert o != null;
            return mapper.readValue(o.toString(), entityClass);
        }catch (Exception e){
            log.error("Exception ", e);
            return  null;
        }
    }

    public void set(String key, Object o, Long tillWhen){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, tillWhen, TimeUnit.MINUTES);
        }catch (Exception e){
            log.error("Exception ", e);
        }
    }
}
