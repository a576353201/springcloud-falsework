package com.fans.threadlocal;

import com.alibaba.fastjson.JSONObject;
import com.fans.user.entity.UserEntity;

/**
 * className: LocalUser
 *
 * @author k
 * @version 1.0
 * @description 本地线程用户存储器
 * @date 2020-06-03 22:03
 **/
public class LocalUser {

    private final static ThreadLocal<JSONObject> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserEntity user, Integer scope) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user" , user);
        jsonObject.put("scope" , scope);
        THREAD_LOCAL.set(jsonObject);
    }

    public static UserEntity getUser() {
        return THREAD_LOCAL.get().getObject("user" , UserEntity.class);
    }

    public static Integer getScope() {
        return THREAD_LOCAL.get().getInteger("scope" );
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
