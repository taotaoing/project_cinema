package com.stylefeng.guns.gateway.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author 申涛涛
 * @date 2019/9/4 12:16
 */
@Configuration
public class JedisConfig {

    @Bean
    public Jedis initJedis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        return jedis;
    }
}
