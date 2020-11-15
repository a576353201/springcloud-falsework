package com.fans.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * className: WeChatBO
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-06-14 11:14
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "微信认证返回信息")
public class WeChatBO implements Serializable {


    private static final long serialVersionUID = -20200614111446L;
    @ApiModelProperty(value = "用户唯一标识")
    private String openid;
    @ApiModelProperty(value = "会话密钥")
    private String session_key;
    @ApiModelProperty(value = "用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。")
    private String unionid;
    /**
     * errcode 的合法值
     * <p>
     * 值	说明	最低版本
     * -1	系统繁忙，此时请开发者稍候再试
     * 0	请求成功
     * 40029	code 无效
     * 45011	频率限制，每个用户每分钟100次
     */
    @ApiModelProperty(value = "错误码")
    private Integer errcode;
    @ApiModelProperty(value = "错误信息")
    private String errmsg;

}
