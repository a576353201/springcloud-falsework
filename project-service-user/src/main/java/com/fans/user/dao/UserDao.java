package com.fans.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fans.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * interfaceName: UserDao
 *
 * @author k
 * @version 1.0
 * @description 数据交互层
 * @date 2020-10-25 14:47:34
 **/
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    List<UserEntity> findAll();

    UserEntity selectByPrimaryKey(@Param("id") Integer id);

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(UserEntity userEntity);

    int insertBatch(@Param("userEntityList") List<UserEntity> userEntityList);

    int updateByPrimaryKeySelective(UserEntity userEntity);

    int updateBatchByPrimaryKeySelective(@Param("userEntityList") List<UserEntity> userEntityList);
}
