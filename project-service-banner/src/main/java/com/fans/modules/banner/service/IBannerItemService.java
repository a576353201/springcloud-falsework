package com.fans.modules.banner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fans.modules.banner.entity.BannerItemEntity;
import com.fans.utils.page.Paging;

import java.util.Map;


/**
 * interfaceName: IBannerItemService
 *
 * @author k
 * @version 1.0
 * @description 服务层
 * @date 2020-10-25 14:47:34
 **/
public interface IBannerItemService extends IService<BannerItemEntity> {

    Paging<BannerItemEntity> queryPage(Map<String, Object> params);
}

