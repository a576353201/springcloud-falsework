package com.fans.core.datasource.properties;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * className: Hikari
 *
 * @author k
 * @version 1.0
 * @description hikari配置
 * @date 2020-10-25 11:20
 **/
@Setter
@Getter
public class Hikari implements Serializable {

    private static final long serialVersionUID = -20201025112004L;

    private long connectionTimeout = 30000;
    private int minimumIdle = 5;
    private int maximumPoolSize = 20;
    private boolean isAutoCommit = true;
    private long idleTimeout = 60000;
    private String poolName = "DateSourceHikariCP";
    private long maxLifetime = 1800000;
    private String connectionTestQuery = "SELECT 1";
}
