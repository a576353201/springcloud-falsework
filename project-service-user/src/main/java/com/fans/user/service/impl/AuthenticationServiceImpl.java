package com.fans.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fans.bo.WeChatBO;
import com.fans.common.constant.CacheKeyConstants;
import com.fans.common.exception.http.ParameterException;
import com.fans.common.utils.JsonUtils;
import com.fans.common.utils.JwtTokenUtils;
import com.fans.user.dao.UserDao;
import com.fans.user.entity.UserEntity;
import com.fans.user.service.IAuthenticationService;
import com.fans.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * className: AuthenticationServiceImpl
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-06-14 11:11
 **/
@Service(value = "iAuthenticationService")
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Value(value = "${wechat.appid}")
    private String appId;
    @Value(value = "${wechat.appsecret}")
    private String appSecret;
    @Value(value = "${wechat.code2session}")
    private String code2SessionUrl;

    @Resource(type = Producer.class)
    private Producer producer;

    @Resource(type = UserDao.class)
    private UserDao userDao;

    @Resource(type = RestTemplate.class)
    private RestTemplate restTemplate;

    @Override
    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2SessionUrl, this.appId, this.appSecret, code);
        WeChatBO weChatBO = null;
        try {
            String result = restTemplate.getForObject(url, String.class);
            weChatBO = JsonUtils.string2Obj(result, WeChatBO.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        log.info("--> {}", JsonUtils.obj2FormattingString(weChatBO));
        assert weChatBO != null;
        return registerUser(weChatBO);
    }

    @Override
    public UserEntity getUserinfoById(Long uid) {
        return userDao.selectById(uid);
    }

    @Override
    public BufferedImage getCaptcha(String uuid) {
        //生成文字验证码
        String code = producer.createText();
        RedisUtils.saveCache(CacheKeyConstants.KAPTCHA, code, 300, uuid);
        return producer.createImage(code);
    }


    private String registerUser(WeChatBO weChatBO) {
        String openId = weChatBO.getOpenid();
        if (StringUtils.isBlank(openId)) {
            throw new ParameterException(20004);
        }
        UserEntity userInfo = userDao.selectOne(
                Wrappers.<UserEntity>lambdaQuery()
                        .eq(UserEntity::getOpenid, openId)
        );
        if (!Optional.ofNullable(userInfo).isPresent()) {
            UserEntity insert = UserEntity.builder()
                    .openid(openId)
                    .build();
            userDao.insert(insert);
            String token = JwtTokenUtils.makeToken(insert.getId());
            if (StringUtils.isNotBlank(token)) {
                setUserInfoToRedis(insert.getId(), userInfo);
            }
            return token;
        }
        //返回jwt令牌
        String token = JwtTokenUtils.makeToken(userInfo.getId());
        if (StringUtils.isNotBlank(token)) {
            setUserInfoToRedis(userInfo.getId(), userInfo);
        }
        return token;
    }

    private void setUserInfoToRedis(Long uid, UserEntity userInfo) {
        //存储登录用户到redis缓存
        RedisUtils.saveCache(CacheKeyConstants.KAPOK, JsonUtils.obj2String(userInfo), 0, uid + StringUtils.EMPTY);
    }


}
