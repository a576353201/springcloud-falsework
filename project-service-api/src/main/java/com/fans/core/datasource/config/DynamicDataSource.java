package com.fans.core.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * className: DynamicDataSource
 *
 * @author k
 * @version 1.0
 * @description 动态选择当前数据源的数据的信息
 * @date 2018-08-04 02:09
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.peek();
    }
}
