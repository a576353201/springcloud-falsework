package com.fans.modules.banner.entity;

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
 * className: BannerItemEntity
 *
 * @author k
 * @version 1.0
 * @description 实体类
 * @date 2020-10-25 14:47:34
 **/
@TableName("banner_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel(value = "实体")
public class BannerItemEntity implements Serializable {

    private static final long serialVersionUID = -6069826505400514845L;

    @TableId
    @ApiModelProperty(value = "", dataType = "integer")
    private Integer id;
    @ApiModelProperty(value = "", dataType = "string")
    private String img;
    @ApiModelProperty(value = "", dataType = "string")
    private String keyword;
    @ApiModelProperty(value = "", dataType = "integer")
    private Integer type;
    @ApiModelProperty(value = "", dataType = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "", dataType = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "", dataType = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;
    @ApiModelProperty(value = "", dataType = "integer")
    private Integer bannerId;
    @ApiModelProperty(value = "", dataType = "string")
    private String name;

}
