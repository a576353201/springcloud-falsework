package com.fans.common.threadpool.eventBean;

import lombok.Builder;

/**
 * className: MessageBean
 *
 * @author k
 * @version 1.0
 * @description 相当于事件入参
 * @date 2019-04-01 10:56
 **/
@Builder
public class MessageBean {
    private String name;
    private String sex;
    private Integer age;

    public MessageBean() {
    }

    public MessageBean(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
