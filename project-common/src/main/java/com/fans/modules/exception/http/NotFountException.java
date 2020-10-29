package com.fans.modules.exception.http;

import org.springframework.http.HttpStatus;

/**
 * className: NotFountException
 *
 * @author k
 * @version 1.0
 * @description 404
 * @date 2020-05-25 21:26
 **/
public class NotFountException extends HttpException {
    public NotFountException(int code) {
        this.code = code;
        this.httpStatus = HttpStatus.NOT_FOUND.value();
    }
}
