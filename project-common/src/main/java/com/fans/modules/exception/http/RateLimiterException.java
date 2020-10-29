package com.fans.modules.exception.http;

import org.springframework.http.HttpStatus;

/**
 * className: RateLimiterException
 *
 * @author k
 * @version 1.0
 * @description 超出流量访问异常
 * @date 2020-05-25 21:26
 **/
public class RateLimiterException extends HttpException {
    public RateLimiterException(int code) {
        this.code = code;
        this.httpStatus = HttpStatus.NOT_ACCEPTABLE.value();
    }
}
