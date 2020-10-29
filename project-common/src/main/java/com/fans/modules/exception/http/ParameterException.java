package com.fans.modules.exception.http;

import org.springframework.http.HttpStatus;

/**
 * className: ParameterException
 *
 * @author k
 * @version 1.0
 * @description 参数校验异常
 * @date 2020-06-01 13:48
 **/
public class ParameterException extends HttpException {

    public ParameterException(Integer code) {
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
    }
}
