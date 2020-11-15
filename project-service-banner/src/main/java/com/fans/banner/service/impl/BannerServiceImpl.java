package com.fans.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fans.banner.dao.BannerDao;
import com.fans.banner.entity.BannerEntity;
import com.fans.banner.service.IBannerService;
import com.fans.banner.vo.BannerVO;
import com.fans.common.utils.JsonUtils;
import com.fans.common.vo.JsonData;
import com.fans.user.controller.AuthenticationControllerApi;
import com.fans.user.entity.UserEntity;
import com.fans.utils.page.PageUtils;
import com.fans.utils.page.Paging;
import com.fans.utils.page.Query;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    @Resource(type = AuthenticationControllerApi.class)
    private AuthenticationControllerApi authenticationControllerApi;

    @Resource(name = "discoveryClient")
    private DiscoveryClient discoveryClient;

    @Resource(type = RestTemplate.class)
    private RestTemplate restTemplate;


    @SuppressWarnings("rawtypes")
    @Override
    public UserEntity getUser(long uid) {
        JsonData<UserEntity> body = authenticationControllerApi.getUserinfoById(1L);

        //*********************直接使用服务名调用******************
        //String url = "http://" + ServiceConstants.SERVICE_USER + "/query?uid=" + uid;
        //ResponseEntity<JsonData> result = restTemplate.postForEntity(url, null, JsonData.class);
        //JsonData body = result.getBody();
        //*********************end******************************

        //*********************DiscoveryClient******************
//        List<ServiceInstance> discoveryClientInstances = discoveryClient.getInstances(ServiceConstants.SERVICE_USER);
//        ServiceInstance serviceInstance = discoveryClientInstances.get(0);
//        String url = "http://" + serviceInstance.getServiceId() + ":" + serviceInstance.getPort() + "/query?uid=" + uid;
//        ResponseEntity<JsonData> result = restTemplate.postForEntity(url, null, JsonData.class);
//        JsonData body = result.getBody();
        //*********************end******************************
        if (body != null) {
            if (body.getCode() == 200) {
                return JsonUtils.string2Obj(JsonUtils.obj2String(body.getBody()), UserEntity.class);
            }
        }
        return null;
    }
}
