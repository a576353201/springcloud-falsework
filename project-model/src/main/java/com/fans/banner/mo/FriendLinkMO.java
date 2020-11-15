package com.fans.banner.mo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * className: FriendLinkMO
 *
 * @author k
 * @version 1.0
 * @description 友情链接模型
 * @date 2020-10-08 20:10
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(value = "FriendLink")
public class FriendLinkMO implements Serializable {

    private static final long serialVersionUID = -20201008201000L;

    @Id
    private String id;
    @Field("link_name")
    private String linkName;
    @Field("link_url")
    private String linkUrl;
    @Field("is_delete")
    private Integer isDelete;
    @Field("create_time")
    private Date createTime;
    @Field("update_time")
    private Date updateTime;

}
