package com.fans.user.fallbacks;

import com.fans.common.vo.AuthenticationInfoVO;
import com.fans.common.vo.JsonData;
import com.fans.common.vo.TokenVerifyResultVO;
import com.fans.user.controller.AuthenticationControllerApi;
import com.fans.user.dto.LoginDTO;
import com.fans.user.dto.TokenDTO;
import com.fans.user.entity.UserEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * className: AuthenticationControllerFallback
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-28 02:02
 **/
@Component
public class AuthenticationControllerFallback implements FallbackFactory<AuthenticationControllerApi> {


    @Override
    public AuthenticationControllerApi create(Throwable throwable) {
        return new AuthenticationControllerApi() {
            @Override
            public AuthenticationInfoVO login(LoginDTO loginDTO) {
                return null;
            }

            @Override
            public TokenVerifyResultVO verify(TokenDTO tokenDTO) {
                return null;
            }

            @Override
            public void captcha(String uuid, HttpServletResponse response) {

            }

            @Override
            public JsonData<UserEntity> getUserinfoById(Long uid) {
                return JsonData.success(UserEntity.builder()
                        .nickname("sssss")
                        .build());
            }
        };
    }
}
