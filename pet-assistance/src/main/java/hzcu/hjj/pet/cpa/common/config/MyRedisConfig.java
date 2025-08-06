package hzcu.hjj.pet.cpa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description: Redis配置
 */
@Configuration
public class MyRedisConfig {
    /**
     * redisTemplate配置
     * @param redisConnectionFactory redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 配置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // 使用Jackson2JsonRedisSerializer配置value的序列化方式
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // 使用FastJson配置value的序列化方式
        // template.setValueSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }
}

