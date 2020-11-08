package com.fans.banner.vo;

import com.fans.banner.entity.BannerItemEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * className: BannerVO
 *
 * @author k
 * @version 1.0
 * @description 轮播图前端展示
 * @date 2020-06-03 14:30
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BannerVO implements Serializable {

    private static final long serialVersionUID = -20200603143031L;

    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    private List<BannerItemEntity> bannerItemList;
}
