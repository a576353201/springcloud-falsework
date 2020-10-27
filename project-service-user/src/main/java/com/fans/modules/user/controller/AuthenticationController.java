package com.fans.modules.user.controller;


import com.fans.annotations.Verify;
import com.fans.enumeration.LoginType;
import com.fans.exception.http.NotFountException;
import com.fans.modules.user.dto.LoginDTO;
import com.fans.modules.user.dto.TokenDTO;
import com.fans.modules.user.service.IAuthenticationService;
import com.fans.utils.EnumUtils;
import com.fans.utils.JwtTokenUtils;
import com.fans.validator.ValidatorUtils;
import com.fans.validator.group.AddGroup;
import com.fans.validator.group.LoginGroup;
import com.fans.vo.AuthenticationInfoVO;
import com.fans.vo.TokenVerifyResultVO;
import org.apache.commons.lang3.StringUtils;
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
public class AuthenticationController implements AuthenticationControllerApi {

    @Resource(name = "iAuthenticationService")
    private IAuthenticationService iAuthenticationService;

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

}
