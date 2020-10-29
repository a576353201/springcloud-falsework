package com.fans.modules.exception.http;

import org.springframework.http.HttpStatus;

/**
 * className: UnAuthenticatedException
 *
 * @author k
 * @version 1.0
 * @description 未授权异常
 * @date 2020-06-01 21:55
 **/
public class UnAuthenticatedException extends HttpException {

    public UnAuthenticatedException(Integer code) {
        this.code = code;
        this.httpStatus = HttpStatus.UNAUTHORIZED.value();
    }
}
