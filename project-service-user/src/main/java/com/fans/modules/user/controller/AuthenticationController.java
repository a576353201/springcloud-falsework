package com.fans.modules.user.controller;


import com.fans.modules.annotations.Verify;
import com.fans.modules.enumeration.LoginType;
import com.fans.modules.exception.http.NotFountException;
import com.fans.modules.user.dto.LoginDTO;
import com.fans.modules.user.dto.TokenDTO;
import com.fans.modules.user.entity.UserEntity;
import com.fans.modules.user.service.IAuthenticationService;
import com.fans.modules.utils.EnumUtils;
import com.fans.modules.utils.JwtTokenUtils;
import com.fans.modules.validator.ValidatorUtils;
import com.fans.modules.validator.group.AddGroup;
import com.fans.modules.validator.group.LoginGroup;
import com.fans.modules.vo.AuthenticationInfoVO;
import com.fans.modules.vo.JsonData;
import com.fans.modules.vo.TokenVerifyResultVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * className: AuthenticationController
 *
 * @author k
 * @version 1.0
 * @description 认证相关控制层
 * @date 2020-06-14 10:58
 **/
@RestController
//熔断方式一 不太实用 @DefaultProperties(defaultFallback = "defaultFallback")
public class AuthenticationController implements AuthenticationControllerApi {

    @Resource(name = "iAuthenticationService")
    private IAuthenticationService iAuthenticationService;

    public JsonData<Object> defaultFallback() {
        return JsonData.fail("全局降级");
    }


    @Override
    public AuthenticationInfoVO login(@RequestBody @Verify(groups = {LoginGroup.class, AddGroup.class}) LoginDTO loginDTO) {
        String token;
        switch (EnumUtils.getByCode(loginDTO.getLoginType(), LoginType.class)) {
            case WE_CHAT:
                token = iAuthenticationService.code2Session(loginDTO.getAccount());
                break;
            case EMAIL:
                //TODO
                token = StringUtils.EMPTY;
                break;
            case MOBILE:
                //TODO
                token = StringUtils.SPACE;
                break;
            default:
                throw new NotFountException(10003);
        }

        return AuthenticationInfoVO.builder()
                .token(token)
                .build();
    }

    @Override
    public TokenVerifyResultVO verify(TokenDTO tokenDTO) {
        ValidatorUtils.check(tokenDTO);
        Boolean valid = JwtTokenUtils.verifyToken(tokenDTO.getToken());
        return TokenVerifyResultVO.builder()
                .isValid(valid)
                .build();
    }

    @Value(value = "${server.port}")
    private Integer port;

    @HystrixCommand //熔断方式（二）粒度更细 推荐使用 (fallbackMethod = "getUserinfoByIdFallback")
    @Override
    public JsonData<UserEntity> getUserinfoById(Long uid) {
        System.err.println("服务端口:" + port);
//        int i = 1 / 0;
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return JsonData.success(iAuthenticationService.getUserinfoById(uid));
    }

    public JsonData<UserEntity> getUserinfoByIdFallback(Long uid) {
        System.err.println("uid:" + uid);
        System.err.println("服务 getUserinfoById降级");
        return JsonData.success(UserEntity.builder()
                .nickname("hahahahaah")
                .build());
    }
}
