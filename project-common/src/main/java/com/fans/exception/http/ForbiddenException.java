package com.fans.exception.http;

import org.springframework.http.HttpStatus;

/**
 * className: ForbiddenException
 *
 * @author k
 * @version 1.0
 * @description 无权限异常
 * @date 2020-06-01 22:05
 **/
public class ForbiddenException extends HttpException {

    public ForbiddenException(Integer code) {
        this.code = code;
        this.httpStatus = HttpStatus.FORBIDDEN.value();
    }
}
