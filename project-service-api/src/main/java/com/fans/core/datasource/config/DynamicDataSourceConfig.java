package com.fans.core.datasource.config;

import com.fans.core.datasource.properties.DataSourceProperties;
import com.fans.core.datasource.properties.DynamicDataSourceProperties;
import com.fans.utils.JsonUtils;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * className: DynamicDataSourceConfig
 *
 * @author k
 * @version 1.0
 * @description 多数据源配置
 * @date 2018-08-04 02:09
 **/
@Configuration
@EnableConfigurationProperties(value = DynamicDataSourceProperties.class)
@Slf4j
public class DynamicDataSourceConfig {

    private static final String DB_PREFIX = "spring.datasource";

    @Resource(name = "dynamicDataSourceProperties")
    private DynamicDataSourceProperties properties;

    private final Map<String, String> contextMap = Maps.newLinkedHashMap();

    @Bean
    @ConfigurationProperties(prefix = DB_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        //默认数据源（若没有默认的数据源则取多数据源配置中第一个）
        HikariDataSource hikariDataSource;
        if (dataSourceProperties.getUrl() == null
                || dataSourceProperties.getPassword() == null
                || dataSourceProperties.getUsername() == null
                || dataSourceProperties.getDriverClassName() == null) {
            hikariDataSource = DynamicDataSourceFactory.build(properties.getDatasource().entrySet().iterator().next().getValue());
        } else {
            hikariDataSource = DynamicDataSourceFactory.build(dataSourceProperties);
            contextMap.put("defaultDatabase", dataSourceProperties.getDesc() == null ? "未配置描述内容（desc）" : dataSourceProperties.getDesc());
        }
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(getDynamicDataSource());
        dataSource.setDefaultTargetDataSource(hikariDataSource);
        log.info("\r\n--> Let's inspect the dataSource provided by Spring Boot to project: \r\n{}", JsonUtils.obj2FormattingString(contextMap));
        return dataSource;
    }

    /**
     * 获取多数据源
     *
     * @return 数据源集合
     */
    private Map<Object, Object> getDynamicDataSource() {
        Map<String, DataSourceProperties> datasource = properties.getDatasource();
        Map<Object, Object> targetDataSource = Maps.newHashMap();
        datasource.forEach((str, properties) -> {
            HikariDataSource hikariDataSource = DynamicDataSourceFactory.build(properties);
            contextMap.put(str, properties.getDesc());
            targetDataSource.put(str, hikariDataSource);
        });
        return targetDataSource;
    }

}
