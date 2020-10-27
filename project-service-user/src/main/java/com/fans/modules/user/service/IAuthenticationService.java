package com.fans.modules.user.service;

/**
 * interfaceName: IAuthenticationService
 *
 * @author k
 * @version 1.0
 * @description 认证服务层
 * @date 2020-06-14 11:09
 **/
public interface IAuthenticationService {
    /**
     * 微信登录，通过code换取用的openId
     *
     * @param code 微信code码
     * @return 认证用的token
     */
    String code2Session(String code);
}
