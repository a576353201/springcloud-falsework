package com.fans.zuul.filters;

import com.fans.common.exception.http.RateLimiterException;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static com.fans.zuul.filters.BaseFilter.forbidden;

/**
 * className: RateLimiterFilter
 *
 * @author k
 * @version 1.0
 * @description 木桶原理
 * @date 2020-10-28 16:14
 **/
@Component
public class RateLimiterFilter extends ZuulFilter {

    @SuppressWarnings("UnstableApiUsage")
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public Object run() throws ZuulException {
        System.out.println("进入木桶限流算法~~~~");
        RequestContext currentContext = RequestContext.getCurrentContext();
        if (!RATE_LIMITER.tryAcquire()) {
            forbidden(currentContext, new RateLimiterException(10002));
        }
        return null;
    }
}
