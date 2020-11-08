package com.fans.exception.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * className: HttpException
 *
 * @author k
 * @version 1.0
 * @description 交互异常处理
 * @date 2020-05-25 21:24
 **/
@Setter
@Getter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
}
