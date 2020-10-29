package com.fans.modules.banner.controller;


import com.fans.modules.constant.CacheKeyConstants;
import com.fans.core.configuration.RabbitmqConfiguration;
import com.fans.modules.exception.http.NotFountException;
import com.fans.modules.banner.entity.BannerEntity;
import com.fans.modules.banner.mo.FriendLinkMO;
import com.fans.modules.banner.repository.MongoDbRepository;
import com.fans.modules.banner.service.IBannerService;
import com.fans.modules.banner.stream.StreamProducer;
import com.fans.modules.banner.vo.BannerVO;
import com.fans.modules.user.entity.UserEntity;
import com.fans.modules.utils.FileUtils;
import com.fans.utils.RedisUtils;
import com.fans.utils.page.PageUtils;
import com.fans.utils.page.Paging;
import com.fans.modules.vo.JsonData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.client.gridfs.GridFSBucket;
import io.swagger.annotations.ApiParam;
import org.bson.types.ObjectId;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * className: BannerController
 *
 * @author k
 * @version 1.0
 * @description 控制层
 * swagger中paramType请求类型注释：
 * (@PathVariable) --> path
 * (@RequestBody) --> body
 * (@RequestPart) --> formData
 * (@RequestHeader) --> header
 * (@RequestParam) --> query，解析方式和无注解时一致
 * @date 2020-10-25 14:47:34
 **/
@RestController
@CacheConfig(cacheNames = "banner")
public class BannerController implements BannerControllerApi {

    @Resource(name = "iBannerService")
    private IBannerService bannerService;

    /**
     * 列表
     */
    @Override
    public JsonData<Paging<BannerVO>> list(@RequestParam(defaultValue = "1", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        Paging<BannerVO> bannerPaging = RedisUtils.getFromCache(CacheKeyConstants.KAPOK, new TypeReference<Paging<BannerVO>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        }, "1", "2");
        if (bannerPaging == null) {
            List<BannerEntity> all = bannerService.list();
            RedisUtils.saveCache(CacheKeyConstants.KAPOK, PageUtils.pageByList(page, pageSize, all, BannerVO.class), 0, "1", "2");
            return JsonData.success("查询录播图列表成功", PageUtils.pageByList(page, pageSize, all, BannerVO.class));
        } else {
            return JsonData.success("查询列表成功", bannerPaging);
        }
    }

    @Override
    @Cacheable(key = "1", unless = "#result.code !=200")
    public JsonData<Paging<BannerVO>> getBannerListByPage(@RequestParam(defaultValue = "1", required = false) Integer page,
                                                          @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        Paging<BannerVO> paging = bannerService.queryPage(PageUtils.pagingPackaging(page, pageSize));
        return JsonData.success("查询录播图列表成功", paging);
    }


    @Override
    @CacheEvict(key = "1", condition = "#result.code == 200")
    public JsonData<String> updateBannerName(@ApiParam(value = "轮播图名称", name = "bannerName") @RequestParam String bannerName) {
        RedisUtils.delCache(CacheKeyConstants.KAPOK, "1");
        return JsonData.success("修改成功");
    }

    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonData<String> rabbitmq() {
        rabbitTemplate.convertAndSend(RabbitmqConfiguration.EXCHANGE_ARTICLE,
                RabbitmqConfiguration.TEST_A,
                "这是一条测试消息");
        return JsonData.success("发送消息成功");
    }

    @Override
    public JsonData<String> delay() {
        MessagePostProcessor messagePostProcessor = message -> {
            //消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            //延迟发送时间 单位ms
            message.getMessageProperties().setDelay(20000);
            return message;
        };
        rabbitTemplate.convertAndSend(RabbitmqConfiguration.EXCHANGE_ARTICLE,
                RabbitmqConfiguration.TEST_A,
                "这是一条测试消息",
                messagePostProcessor);
        return JsonData.success("发送消息成功");
    }

    @Resource(name = "gridFSBucket")
    private GridFSBucket gridFSBucket;

    @Override
    public JsonData<String> gridfs() throws FileNotFoundException {
        ObjectId objectId = gridFSBucket.uploadFromStream("banner.txt", new FileInputStream(new File("C:\\work\\project\\jpa-falsework\\src\\main\\resources\\banner.txt")));
        return JsonData.success("存储文件成功,Id为：" + objectId.toString());
    }

    @Resource(type = MongoDbRepository.class)
    private MongoDbRepository mongoDbRepository;

    @Override
    public JsonData<List<FriendLinkMO>> mongodb() {

        //新增
        FriendLinkMO linkMO = mongoDbRepository.save(FriendLinkMO.builder()
                .id("1")
                .linkName("淘宝")
                .linkUrl("www.baidu.com")
                .isDelete(0)
                .createTime(new Date())
                .updateTime(new Date())
                .build());
        //修改
        linkMO.setLinkName("京东");
        mongoDbRepository.save(linkMO);
        //查询所有
        List<FriendLinkMO> all = mongoDbRepository.findAll();
        //删除
        mongoDbRepository.deleteById(linkMO.getId());
        return JsonData.success(all);
    }


    @Override
    public void download(HttpServletRequest request, HttpServletResponse response) {
        FileUtils.downloadFileByStream(request, response, new File("C:\\Users\\Administrator\\Downloads\\m2.png"), false);
    }

    @Override
    public void urlDownload(HttpServletRequest request, HttpServletResponse response) {
        FileUtils.downloadFileByUrl(request, response, "http://192.168.0.6:8888/kapok/M00/00/00/wKgAaF925a-AIoQUAARuZa3IVd8481.jpg");
    }

    @Override
    public void test() {
        throw new NotFountException(10000);
    }

    @Override
    public JsonData<UserEntity> getUser() {
        UserEntity userEntity = bannerService.getUser(1L);
        return JsonData.success(userEntity);
    }


    @Resource(name = "streamProducer")
    private StreamProducer streamProducer;

    @Override
    public JsonData<String> stream() {
        for (int i = 0; i < 10; i++) {
            streamProducer.sendMessage(i + 1 + "");
        }
        return JsonData.success("吃饺子成功, 我进行了group配置 你们不能争抢了，同时消息也被持久化了~~~~");
    }

}
