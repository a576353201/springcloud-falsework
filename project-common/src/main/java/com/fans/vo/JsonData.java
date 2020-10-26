package com.fans.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * className: GlobalExceptionAdvice
 *
 * @author k
 * @version 1.0
 * @description 自定义交互数据定义类
 * @date 2018-11-20 09:44
 **/
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "通用返参")
public class JsonData<T> implements Serializable {

    private static final long serialVersionUID = 123456789L;
    @ApiModelProperty(value = "状态码", dataType = "int")
    private Integer code;
    @ApiModelProperty(value = "信息", dataType = "string")
    private String msg;
    @ApiModelProperty(value = "返回数据", dataType = "object")
    private T body;
    @ApiModelProperty(value = "请求信息(抛出异常时携带)", dataType = "string")
    private String request;

    private JsonData(Integer code) {
        this.code = code;
    }

    private JsonData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonData(Integer code, T body) {
        this.code = code;
        this.body = body;
    }

    private JsonData(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    private JsonData(Integer code, String msg, String request) {
        this.code = code;
        this.msg = msg;
        this.request = request;
    }

    public static <T> JsonData<T> success(String msg, T data) {
        return new JsonData<>(HttpStatus.OK.value(), msg, data);
    }

    public static <T> JsonData<T> success(T data) {
        return new JsonData<>(HttpStatus.OK.value(), data);
    }

    public static <T> JsonData<T> success(String msg) {
        return new JsonData<>(HttpStatus.OK.value(), msg);
    }

    public static <T> JsonData<T> success() {
        return new JsonData<>(HttpStatus.OK.value());
    }

    public static <T> JsonData<T> fail(String msg) {
        return new JsonData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> JsonData<T> fail(String msg, String request) {
        return new JsonData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, request);
    }

    public static <T> JsonData<T> failCodeMsg(Integer code, String msg) {
        return new JsonData<>(code, msg);
    }

    public static <T> JsonData<T> failCodeMsg(Integer code, String msg, String request) {
        return new JsonData<>(code, msg, request);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code.equals(HttpStatus.OK.value());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("body", body);
        result.put("request", request);
        return result;
    }
}
