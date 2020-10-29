package com.fans.modules.zuul.filters;

import com.fans.modules.constant.CacheKeyConstants;
import com.fans.modules.exception.http.RateLimiterException;
import com.fans.modules.utils.NetWorkUtils;
import com.fans.utils.RedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.fans.modules.zuul.filters.BaseFilter.forbidden;

/**
 * className: IpFilter
 *
 * @author k
 * @version 1.0
 * @description 默认filter
 * @date 2020-10-23 05:38
 **/
@Component
@RefreshScope
public class IpFilter extends ZuulFilter {

    @Value(value = "${blackIp.continueCounts}")
    private Integer continueCounts;
    @Value(value = "${blackIp.timeInterval}")
    private Integer timeInterval;
    @Value(value = "${blackIp.limitTimes}")
    private Integer limitTimes;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Ip网关过滤器");

        System.err.println("continueCounts:" + continueCounts);
        System.err.println("timeInterval: " + timeInterval);
        System.err.println("limitTimes:" + limitTimes);

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String ipAddress = NetWorkUtils.getIpAddress(request);

        // 判断限制时间还剩多少秒  在限制时间内 点击次数进行累加
        Long ttl = RedisUtils.ttl(CacheKeyConstants.LIMIT_TIMES, ipAddress);
        if (ttl > 0) {
            forbidden(currentContext, new RateLimiterException(10002));
            return null;
        }

        //第一次进入 设置限制时间
        Long clickCount = RedisUtils.incrementAndGet(CacheKeyConstants.CLICK_COUNTS, 1, ipAddress);
        if (clickCount == 1) {
            RedisUtils.setExpire(CacheKeyConstants.CLICK_COUNTS, 20, ipAddress);
        }

        //如果点击次数超过 配置次数 则进行限制访问
        if (clickCount > continueCounts) {
            RedisUtils.saveCache(CacheKeyConstants.LIMIT_TIMES, CacheKeyConstants.LIMIT_TIMES.name(), limitTimes, ipAddress);
            forbidden(currentContext, new RateLimiterException(10002));
        }
        return null;
    }
}
