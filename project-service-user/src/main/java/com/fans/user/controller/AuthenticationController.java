package com.fans.user.controller;


import com.fans.common.annotations.Verify;
import com.fans.common.enumeration.LoginType;
import com.fans.common.exception.http.NotFountException;
import com.fans.common.utils.EnumUtils;
import com.fans.common.utils.JwtTokenUtils;
import com.fans.common.validator.ValidatorUtils;
import com.fans.common.validator.group.AddGroup;
import com.fans.common.validator.group.LoginGroup;
import com.fans.common.vo.AuthenticationInfoVO;
import com.fans.common.vo.JsonData;
import com.fans.common.vo.TokenVerifyResultVO;
import com.fans.user.dto.LoginDTO;
import com.fans.user.dto.TokenDTO;
import com.fans.user.entity.UserEntity;
import com.fans.user.service.IAuthenticationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    @Resource(name = "iAuthenticationService" )
    private IAuthenticationService iAuthenticationService;

    public JsonData<Object> defaultFallback() {
        return JsonData.fail("全局降级" );
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

    @Override
    public void captcha(String uuid, HttpServletResponse response) {
        response.setHeader("Cache-Control" , "no-store, no-cache" );
        response.setContentType("image/jpeg" );
        //获取图片验证码
        BufferedImage image = iAuthenticationService.getCaptcha(uuid);
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg" , out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value(value = "${server.port}" )
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
        System.err.println("服务 getUserinfoById降级" );
        return JsonData.success(UserEntity.builder()
                .nickname("hahahahaah" )
                .build());
    }
}
