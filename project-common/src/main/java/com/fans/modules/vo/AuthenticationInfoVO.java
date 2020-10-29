package com.fans.modules.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * className: AuthenticationInfoVO
 *
 * @author k
 * @version 1.0
 * @description 认证返回参数
 * @date 2020-06-14 11:04
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "认证后返回信息")
public class AuthenticationInfoVO implements Serializable {

    private static final long serialVersionUID = -20200614110447L;

    @ApiModelProperty(value = "认证token")
    private String token;

}
