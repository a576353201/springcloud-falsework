package com.fans.common.enumeration;

/**
 * enumName: LoginType
 *
 * @author k
 * @version 1.0
 * @description 登录类型
 * @date 2020-06-04 15:16
 **/
public enum LoginType implements CodeEnum {
    /**
     * 0 邮箱 1 微信 2 手机号
     */
    EMAIL(0, "邮箱登录" ),
    WE_CHAT(1, "微信登录" ),
    MOBILE(2, "手机号登录" );

    private final Integer code;
    private final String desc;

    LoginType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
