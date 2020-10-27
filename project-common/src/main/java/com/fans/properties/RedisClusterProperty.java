package com.fans.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * className: RedisClusterProperty
 *
 * @author k
 * @version 1.0
 * @description redis集群集合
 * @date 2020-10-25 20:09
 **/
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.redis.sharded-cluster")
public class RedisClusterProperty {
    private List<String> nodes;
}
