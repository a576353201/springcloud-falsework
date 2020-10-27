package com.fans.properties.bean;

import com.google.common.collect.Lists;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * className: InterceptorBean
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-27 13:35
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class InterceptorBean implements Serializable {


    private List<String> addPathPatterns = Lists.newArrayList();

    private List<String> excludePathPatterns = Lists.newArrayList();

}
