package com.fans.modules.banner.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fans.modules.banner.entity.BannerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * interfaceName: BannerDao
 *
 * @author k
 * @version 1.0
 * @description 数据交互层
 * @date 2020-10-25 14:47:34
 **/
@Mapper
public interface BannerDao extends BaseMapper<BannerEntity> {

    List<BannerEntity> findAll();

    BannerEntity selectByPrimaryKey(@Param("id")  Integer id);

    int deleteByPrimaryKey(@Param("id")  Integer id);

    int insertSelective(BannerEntity bannerEntity);

    int insertBatch(@Param("bannerEntityList") List<BannerEntity> bannerEntityList);

    int updateByPrimaryKeySelective(BannerEntity bannerEntity);

    int updateBatchByPrimaryKeySelective(@Param("bannerEntityList") List<BannerEntity> bannerEntityList);
}
