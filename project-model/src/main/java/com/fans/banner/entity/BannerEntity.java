package com.fans.banner.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * className: BannerEntity
 *
 * @author k
 * @version 1.0
 * @description 实体类
 * @date 2020-10-25 14:47:34
 **/
@TableName("banner" )
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "实体" )
public class BannerEntity implements Serializable {

    private static final long serialVersionUID = -6123889349750361441L;

    @TableId
    @ApiModelProperty(value = "" , dataType = "integer" )
    private Integer id;
    @ApiModelProperty(value = "" , dataType = "string" )
    @NotBlank(message = "不能为空" )
    private String name;
    @ApiModelProperty(value = "" , dataType = "string" )
    private String description;
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
    @ApiModelProperty(value = "" , dataType = "string" )
    private String title;
    @ApiModelProperty(value = "部分banner可能有标题图片" , dataType = "string" )
    private String img;

}
