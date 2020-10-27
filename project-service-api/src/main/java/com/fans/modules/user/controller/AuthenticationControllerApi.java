package com.fans.modules.user.controller;

import com.fans.annotations.Verify;
import com.fans.modules.user.dto.LoginDTO;
import com.fans.modules.user.dto.TokenDTO;
import com.fans.validator.group.AddGroup;
import com.fans.validator.group.LoginGroup;
import com.fans.vo.AuthenticationInfoVO;
import com.fans.vo.TokenVerifyResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * interfaceName: AuthenticationControllerApi
 *
 * @author k
 * @version 1.0
 * @description 认证相关api
 * @date 2020-10-26 21:25
 **/
@Api(value = "登录认证-api", tags = "登录认证-api")
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
}
