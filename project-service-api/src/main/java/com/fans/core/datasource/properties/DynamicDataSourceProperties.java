package com.fans.core.datasource.properties;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * className: DynamicDataSourceProperties
 *
 * @author k
 * @version 1.0
 * @description 多数据源配置属性
 * @date 2018-08-04 02:09
 **/
@Component(value = "dynamicDataSourceProperties" )
@ConfigurationProperties(prefix = "dynamic" )
@Data
public class DynamicDataSourceProperties {
    /**
     * 数据源集合
     */
    private Map<String, DataSourceProperties> datasource = Maps.newLinkedHashMap();

}
