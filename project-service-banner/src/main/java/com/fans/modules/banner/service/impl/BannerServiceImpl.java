package com.fans.modules.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fans.modules.banner.dao.BannerDao;
import com.fans.modules.banner.entity.BannerEntity;
import com.fans.modules.banner.service.IBannerService;
import com.fans.modules.banner.vo.BannerVO;
import com.fans.utils.page.PageUtils;
import com.fans.utils.page.Paging;
import com.fans.utils.page.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * className: BannerServiceImpl
 *
 * @author k
 * @version 1.0
 * @description 服务实现层
 * @date 2020-10-25 14:47:34
 **/
@Service("iBannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements IBannerService {

    @Resource(type = BannerDao.class)
    private BannerDao bannerDao;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public Paging<BannerVO> queryPage(Map<String, Object> params) {
        IPage<BannerEntity> page = this.page(
                new Query<BannerEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return PageUtils.page(page, BannerVO.class);
    }
}
