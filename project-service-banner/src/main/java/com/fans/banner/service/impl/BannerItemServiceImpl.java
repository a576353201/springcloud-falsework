package com.fans.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fans.banner.dao.BannerItemDao;
import com.fans.banner.entity.BannerItemEntity;
import com.fans.banner.service.IBannerItemService;
import com.fans.utils.page.PageUtils;
import com.fans.utils.page.Paging;
import com.fans.utils.page.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * className: BannerItemServiceImpl
 *
 * @author k
 * @version 1.0
 * @description 服务实现层
 * @date 2020-10-25 14:47:34
 **/
@Service("iBannerItemService")
public class BannerItemServiceImpl extends ServiceImpl<BannerItemDao, BannerItemEntity> implements IBannerItemService {

    @Resource(type = BannerItemDao.class)
    private BannerItemDao bannerItemDao;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public Paging<BannerItemEntity> queryPage(Map<String, Object> params) {
        IPage<BannerItemEntity> page = this.page(
                new Query<BannerItemEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return PageUtils.page(page);
    }

}
