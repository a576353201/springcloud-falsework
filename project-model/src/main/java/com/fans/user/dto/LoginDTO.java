package com.fans.user.dto;


import com.fans.annotations.Password;
import com.fans.validator.group.AddGroup;
import com.fans.validator.group.LoginGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * className: LoginDTO
 *
 * @author k
 * @version 1.0
 * @description 登录入参
 * @date 2020-06-04 15:31
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "登录入参")
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -20200604153110L;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不允许为空！！！", groups = {AddGroup.class})
    private String account;

    @ApiModelProperty(value = "密码")
    @Password(max = 30, message = "{password.validate}", groups = {LoginGroup.class})
    private String password;
    /**
     * 见 LoginType
     */
    @ApiModelProperty(value = "登录类型 0 邮箱 1 微信 2 手机号")
    private Integer loginType;
}
