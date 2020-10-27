package com.fans.core.datasource.config;

import com.fans.core.datasource.properties.Hikari;
import com.fans.core.datasource.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * className: DynamicDataSourceFactory
 *
 * @author k
 * @version 1.0
 * @description druid和datasource配置赋值
 * @date 2018-08-04 02:09
 **/
@Slf4j
public class DynamicDataSourceFactory {

    public static HikariDataSource build(DataSourceProperties properties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(properties.getDriverClassName());
        hikariDataSource.setJdbcUrl(properties.getUrl());
        hikariDataSource.setUsername(properties.getUsername());
        hikariDataSource.setPassword(properties.getPassword());
        Hikari hikari = properties.getHikari();
        if (hikari == null) {
            hikari = new Hikari();
        }
        hikariDataSource.setConnectionTimeout(hikari.getConnectionTimeout());
        hikariDataSource.setMinimumIdle(hikari.getMinimumIdle());
        hikariDataSource.setMaximumPoolSize(hikari.getMaximumPoolSize());
        hikariDataSource.setAutoCommit(hikari.isAutoCommit());
        hikariDataSource.setIdleTimeout(hikari.getIdleTimeout());
        hikariDataSource.setPoolName(hikari.getPoolName());
        hikariDataSource.setMaxLifetime(hikari.getMaxLifetime());
        hikariDataSource.setConnectionTestQuery(hikari.getConnectionTestQuery());
        return hikariDataSource;
    }
}
