package com.fans.core.exception;

import com.fans.common.exception.ValidatorException;
import com.fans.common.exception.http.HttpException;
import com.fans.common.properties.ExceptionProperty;
import com.fans.common.vo.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * className: GlobalExceptionAdvice
 *
 * @author k
 * @version 1.0
 * @description 全局异常拦截
 * @date 2020-05-24 15:38
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @Resource(type = ExceptionProperty.class)
    private ExceptionProperty exceptionProperty;

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonData<?> handleHttpException(HttpServletRequest request, Exception exception) {
        log.error("--> Exception : {}", exception.getMessage(), exception);
        String uri = request.getRequestURI();
        String method = request.getMethod();
        return JsonData.failCodeMsg(9999, exception.getMessage(), method + StringUtils.SPACE + uri);
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<JsonData<?>> handleHttpException(HttpServletRequest request, HttpException httpException) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        JsonData<?> jsonData = JsonData.failCodeMsg(httpException.getCode(),
                exceptionProperty.getMessage(httpException.getCode()),
                method + StringUtils.SPACE + uri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(httpException.getHttpStatus());
        assert httpStatus != null;
        return new ResponseEntity<>(jsonData, headers, httpStatus);
    }

    @ExceptionHandler(value = ValidatorException.class)
    public ResponseEntity<JsonData<?>> handleValidatorException(HttpServletRequest request, ValidatorException validatorException) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        JsonData<?> jsonData = JsonData.fail(validatorException.getMessage(),
                method + StringUtils.SPACE + uri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(validatorException.getHttpStatus());
        assert httpStatus != null;
        return new ResponseEntity<>(jsonData, headers, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonData<?> handleBeanValidation(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        return JsonData.failCodeMsg(10001, e.getMessage().substring(e.getMessage().indexOf(".") + 1), method + " " + requestUrl);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonData<?> handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return JsonData.failCodeMsg(10001, message, method + " " + requestUrl);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';').append(" ")
        );
        return errorMsg.toString();
    }
}
