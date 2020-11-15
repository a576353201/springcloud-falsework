package com.fans.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * className: UserEntity
 *
 * @author k
 * @version 1.0
 * @description 实体类
 * @date 2020-10-25 14:47:34
 **/
@TableName("user" )
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "实体" )
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -8312552230631877612L;

    @TableId
    @ApiModelProperty(value = "" , dataType = "long" )
    private Long id;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String openid;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String nickname;
    @ApiModelProperty(value = "" , dataType = "integer" )
    private Integer unifyUid;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String email;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String password;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String mobile;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String wxProfile;
    @ApiModelProperty(value = "" , dataType = "date" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8" )
    private Date createTime;
    @ApiModelProperty(value = "" , dataType = "date" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8" )
    private Date updateTime;
    @ApiModelProperty(value = "" , dataType = "date" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8" )
    private Date deleteTime;

}
