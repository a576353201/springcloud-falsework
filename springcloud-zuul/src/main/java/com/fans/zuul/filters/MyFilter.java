package com.fans.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * className: MyFilter
 *
 * @author k
 * @version 1.0
 * @description 自定义filter
 * @date 2020-10-28 13:50
 **/
@Component
public class MyFilter extends ZuulFilter {

    /**
     * pre：    在请求被路由之前执行
     * route：  在路由请求的时候执行
     * post：   请求路由以后执行
     * error：  处理请求时发生错误的时候执行
     *
     * @return 定义过滤器的类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行的顺序，配置多个有顺序的过滤
     * 执行顺序从小到大
     *
     * @return 过滤等级
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启过滤器
     * true：开启
     * false：禁用
     *
     * @return 开关
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的业务实现
     *
     * @return 业务对象 没有意义可以不用管。
     * @throws ZuulException 异常
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("我的第一个网关过滤器");
        return null;
    }
}