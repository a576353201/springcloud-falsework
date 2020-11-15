package com.fans.core.configuration;

import com.fans.core.conditionals.RedisConditional;
import com.fans.common.properties.RedisClusterProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
 * className: RedisConfiguration
 *
 * @author k
 * @version 1.0
 * @description redis数据源配置
 * @date 2018-12-20 14:14
 **/
@Configuration
@EnableCaching
@Conditional(RedisConditional.class)
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {
    @Resource
    private RedisProperties redisProperties;
    @Resource
    private RedisClusterProperty redisClusterProperty;

    @Value(value = "${spring.cache.redis.time-to-live}")
    private String timeToLive;

    private final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

    public RedisConfiguration() {
        //设置序列化
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        ObjectMapper objectMapper = new ObjectMapper();
        //指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    }

    private JedisPoolConfig assemble() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        return config;
    }

    @Bean
    public ShardedJedisPool getShardedJdsPool() {
        JedisPoolConfig config = assemble();
        List<JedisShardInfo> jdsInfoList = Lists.newArrayList();
        if (redisClusterProperty == null) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().getSeconds() * 1000);
            jedisShardInfo.setPassword(redisProperties.getPassword());
            return new ShardedJedisPool(config, Lists.newArrayList(jedisShardInfo));
        }
        List<String> nodes = Lists.newArrayList(redisClusterProperty.getNodes());
        nodes.remove(redisProperties.getHost() + ":" + redisProperties.getPort());
        if (nodes.isEmpty()) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().getSeconds() * 1000);
            jedisShardInfo.setPassword(redisProperties.getPassword());
            return new ShardedJedisPool(config, Lists.newArrayList(jedisShardInfo));
        }
        nodes.forEach(host -> {
            String[] url = host.split(":");
            if (StringUtils.isBlank(url[1])) {
                url[1] = "6379";
            }
            JedisShardInfo jedisShardInfo = new JedisShardInfo(url[0], Integer.parseInt(url[1]), (int) redisProperties.getTimeout().getSeconds() * 1000);
            jedisShardInfo.setPassword(jedisShardInfo.getPassword());
            jdsInfoList.add(jedisShardInfo);
        });
        return new ShardedJedisPool(config, jdsInfoList);
    }

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = assemble();
        return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().getSeconds() * 1000, redisProperties.getPassword(), redisProperties.getDatabase(), redisProperties.isSsl());
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //失效时间
                .entryTtl(Duration.parse(timeToLive))
                // 使用StringRedisSerializer来序列化和反序列化redis的key值
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                // 禁用空值
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        //key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        //value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        //Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
