package com.fans.banner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fans.banner.entity.BannerItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * interfaceName: BannerItemDao
 *
 * @author k
 * @version 1.0
 * @description 数据交互层
 * @date 2020-10-25 14:47:34
 **/
@Mapper
public interface BannerItemDao extends BaseMapper<BannerItemEntity> {

    List<BannerItemEntity> findAll();

    BannerItemEntity selectByPrimaryKey(@Param("id" ) Integer id);

    int deleteByPrimaryKey(@Param("id" ) Integer id);

    int insertSelective(BannerItemEntity bannerItemEntity);

    int insertBatch(@Param("bannerItemEntityList" ) List<BannerItemEntity> bannerItemEntityList);

    int updateByPrimaryKeySelective(BannerItemEntity bannerItemEntity);

    int updateBatchByPrimaryKeySelective(@Param("bannerItemEntityList" ) List<BannerItemEntity> bannerItemEntityList);
}
