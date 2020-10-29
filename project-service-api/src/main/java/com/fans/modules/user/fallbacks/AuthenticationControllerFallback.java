package com.fans.modules.user.fallbacks;

import com.fans.modules.user.controller.AuthenticationControllerApi;
import com.fans.modules.user.dto.LoginDTO;
import com.fans.modules.user.dto.TokenDTO;
import com.fans.modules.user.entity.UserEntity;
import com.fans.modules.vo.AuthenticationInfoVO;
import com.fans.modules.vo.JsonData;
import com.fans.modules.vo.TokenVerifyResultVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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
            public JsonData<UserEntity> getUserinfoById(Long uid) {
                return JsonData.success(UserEntity.builder()
                        .nickname("sssss")
                        .build());
            }
        };
    }
}
