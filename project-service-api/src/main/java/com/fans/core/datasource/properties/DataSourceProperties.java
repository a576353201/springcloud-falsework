package com.fans.core.datasource.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * className: DataSourceProperties
 *
 * @author k
 * @version 1.0
 * @description 多数据源属性
 * @date 2018-08-04 02:09
 **/
@Setter
@Getter
public class DataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String desc;
    private Hikari hikari;


}
