package com.fans.modules.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * className: TokenDTO
 *
 * @author k
 * @version 1.0
 * @description token验证
 * @date 2020-06-14 15:51
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = -20200614155116L;

    @NotBlank(message = "{token.validate}")
    private String token;
}
