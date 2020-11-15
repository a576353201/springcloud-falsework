package com.fans.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * className: ValidatorException
 *
 * @author k
 * @version 1.0
 * @description 参数校验异常
 * @date 2020-06-14 12:41
 **/
@Setter
@Getter
public class ValidatorException extends RuntimeException {
    private Integer httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private String message;

    public ValidatorException(String message) {
        super(message);
        this.message = message;
    }
}
