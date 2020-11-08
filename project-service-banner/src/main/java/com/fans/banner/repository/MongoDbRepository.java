package com.fans.banner.repository;


import com.fans.banner.mo.FriendLinkMO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * interfaceName: MongoDbRepository
 *
 * @author k
 * @version 1.0
 * @description mongodb测试
 * @date 2020-10-24 15:29
 **/
public interface MongoDbRepository extends MongoRepository<FriendLinkMO, String> {

}
