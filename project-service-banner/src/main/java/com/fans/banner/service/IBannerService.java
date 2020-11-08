package com.fans.banner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fans.banner.entity.BannerEntity;
import com.fans.banner.vo.BannerVO;
import com.fans.user.entity.UserEntity;
import com.fans.utils.page.Paging;

import java.util.Map;


/**
 * interfaceName: IBannerService
 *
 * @author k
 * @version 1.0
 * @description 服务层
 * @date 2020-10-25 14:47:34
 **/
public interface IBannerService extends IService<BannerEntity> {

    Paging<BannerVO> queryPage(Map<String, Object> params);

    UserEntity getUser(long uid);
}

