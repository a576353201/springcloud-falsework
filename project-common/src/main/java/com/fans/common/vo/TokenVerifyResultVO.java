package com.fans.common.vo;

import lombok.*;

import java.io.Serializable;

/**
 * className: TokenVerifyResultVO
 *
 * @author k
 * @version 1.0
 * @description token认证结果
 * @date 2020-06-14 11:37
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TokenVerifyResultVO implements Serializable {

    private static final long serialVersionUID = -20200614113745L;

    private Boolean isValid;

}
