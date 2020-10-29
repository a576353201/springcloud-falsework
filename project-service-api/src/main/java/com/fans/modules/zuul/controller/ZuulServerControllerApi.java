package com.fans.modules.zuul.controller;

import com.fans.modules.vo.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * interfaceName: ZuulServerControllerApi
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-29 23:52
 **/
@Api(value = "配置中心api", tags = "配置中心api")
public interface ZuulServerControllerApi {

    @ApiOperation(value = "zuul网关错误拦截", notes = "zuul网关错误拦截", httpMethod = "GET")
    @GetMapping(value = "/error")
    JsonData<?> error(HttpServletRequest request);

    @ApiOperation(value = "刷新所有云端配置api刷 需要配置rabbitMq", notes = "刷新所有云端配置api 需要配置rabbitMq", httpMethod = "POST")
    @PostMapping(value = "/refreshAll")
    JsonData<String> refreshAll();
}
