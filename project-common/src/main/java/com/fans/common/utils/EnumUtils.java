package com.fans.common.utils;

import com.fans.common.enumeration.CodeEnum;

import java.util.Arrays;

/**
 * className: EnumUtils
 *
 * @author k
 * @version 1.0
 * @description 枚举类描述信息获取
 * @date 2018-12-20 14:14
 **/
public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}
