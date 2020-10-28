package com.fans.modules.user.controller;

import com.fans.annotations.Verify;
import com.fans.constant.ServiceConstants;
import com.fans.modules.user.dto.LoginDTO;
import com.fans.modules.user.dto.TokenDTO;
import com.fans.modules.user.entity.UserEntity;
import com.fans.modules.user.fallbacks.AuthenticationControllerFallback;
import com.fans.validator.group.AddGroup;
import com.fans.validator.group.LoginGroup;
import com.fans.vo.AuthenticationInfoVO;
import com.fans.vo.JsonData;
import com.fans.vo.TokenVerifyResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * interfaceName: AuthenticationControllerApi
 *
 * @author k
 * @version 1.0
 * @description 认证相关api
 * @date 2020-10-26 21:25
 **/
@Api(value = "登录认证-api", tags = "登录认证-api")
// 客户端（调用方）熔断 如果服务直接挂掉将会走这里   feign内置的熔断机制 由客户端配置
@FeignClient(value = ServiceConstants.SERVICE_USER, fallbackFactory = AuthenticationControllerFallback.class)
public interface AuthenticationControllerApi {

    @PostMapping(value = "/login")
    @ApiOperation(value = "登录认证 0 邮箱 1 微信 2 手机号", notes = "登录认证 0 邮箱 1 微信 2 手机号")
    AuthenticationInfoVO login(@RequestBody @Verify(groups = {LoginGroup.class, AddGroup.class}) LoginDTO loginDTO);

    @PostMapping(value = "/verify")
    @ApiOperation(value = "token是否有效认证", notes = "token是否有效认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "认证token", paramType = "query", dataType = "string")
    })
    TokenVerifyResultVO verify(TokenDTO tokenDTO);

    @PostMapping(value = "/query")
    @ApiOperation(value = "查询用户信息通过id", notes = "查询用户信息通过id", httpMethod = "POST")
    JsonData<UserEntity> getUserinfoById(@RequestParam Long uid);
}
