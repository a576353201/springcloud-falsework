package com.fans.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * className: KaptchaProperty
 *
 * @author k
 * @version 1.0
 * @description 验证码配置
 * @date 2020-10-30 09:35
 **/
@Component
@ConfigurationProperties(prefix = "kaptcha" )
@Setter
@Getter
public class KaptchaProperty {

    /**
     * 图片边框,合法值:yes,no
     */
    private String border = "yes";
    /**
     * 边框颜色 合法值: r.g,b(and optional alpha)或者 white,black,blue.
     */
    private String borderColor = "black";
    /**
     * 边框厚度。合法值: > 0
     */
    private Integer borderThickness = 1;

    /**
     * 图片宽
     */
    private Integer imageWidth = 200;
    /**
     * 图片高
     */
    private Integer imageHeight = 50;
    /**
     * 图片实现类
     */
    private String producerImpl = "com.google.code.kaptcha.impl.DefaultKaptcha";
    /**
     * 文本实现类
     */
    private String textProducerImpl = "com.google.code.kaptcha.text.impl.DefaultTextCreator";
    /**
     * 文本集合,验证码值从此集合中获取
     */
    private String textProducerCharString = "abcde2345678gfynmnpwx";
    /**
     * 验证码长度
     */
    private Integer textProducerCharLength = 5;
    /**
     * 字体
     */
    private String textProducerFontNames = "Arial,Courier";
    /**
     * 字体大小
     */
    private Integer textProducerFontSize = 40;
    /**
     * 字体颜色,合法值:r,g,b 或者 white,black,blue.
     */
    private String textProducerFontColor = "black";
    /**
     * 文字间隔
     */
    private Integer textProducerCharSpace = 2;
    /**
     * 干扰实现类
     */
    private String noiseImpl = "com.google.code.kaptcha.impl.DefaultNoise";
    /**
     * 干扰颜色合法值:r,g,b或者white,black,blue.
     */
    private String noiseColor = "black";
    /**
     * 图片样式:
     * 水纹com.google.code.kaptcha.impl.WaterRipple
     * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
     * 阴影com.google.code.kaptcha.impl.ShadowGimpy"
     */
    private String obscurificatorImpl = "com.google.code.kaptcha.impl.WaterRipple";
    /**
     * 背景实现类
     */
    private String backgroundImpl = "com.google.code.kaptcha.impl.DefaultBackground";
    /**
     * 背景颜色渐变,开始颜色
     */
    private String backgroundClearFrom = "lightGray";
    /**
     * 背景颜色渐变。结束颜色
     */
    private String backgroundClearTo = "white";
    /**
     * 文字渲染器
     */
    private String wordImpl = "com.google.code.kaptcha.text.impl.DefaultWordRenderer";
}
