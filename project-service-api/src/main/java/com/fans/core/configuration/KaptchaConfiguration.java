package com.fans.core.configuration;

import com.fans.common.properties.KaptchaProperty;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;


/**
 * className: DefaultKaptcha
 *
 * @author k
 * @version 1.0
 * @description 生成验证码配置
 * @date 2018-12-20 11:40
 **/
@Configuration
public class KaptchaConfiguration {

    @Resource(name = "kaptchaProperty")
    private KaptchaProperty kaptchaProperty;

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", kaptchaProperty.getBorder());
        properties.put("kaptcha.border.color", kaptchaProperty.getBorderColor());
        properties.put("kaptcha.border.thickness", kaptchaProperty.getBorderThickness());
        properties.put("kaptcha.image.width", kaptchaProperty.getImageWidth());
        properties.put("kaptcha.image.height", kaptchaProperty.getImageHeight());
        properties.put("kaptcha.producer.impl", kaptchaProperty.getProducerImpl());
        properties.put("kaptcha.textproducer.impl", kaptchaProperty.getTextProducerImpl());
        properties.put("kaptcha.textproducer.char.string", kaptchaProperty.getTextProducerCharString());
        properties.put("kaptcha.textproducer.char.length", kaptchaProperty.getTextProducerCharLength());
        properties.put("kaptcha.textproducer.font.names", kaptchaProperty.getTextProducerFontNames());
        properties.put("kaptcha.textproducer.font.size", kaptchaProperty.getTextProducerFontSize());
        properties.put("kaptcha.textproducer.font.color", kaptchaProperty.getTextProducerFontColor());
        properties.put("kaptcha.textproducer.char.space", kaptchaProperty.getTextProducerCharSpace());
        properties.put("kaptcha.noise.impl", kaptchaProperty.getNoiseImpl());
        properties.put("kaptcha.noise.color", kaptchaProperty.getNoiseColor());
        properties.put("kaptcha.obscurificator.impl", kaptchaProperty.getObscurificatorImpl());
        properties.put("kaptcha.background.impl", kaptchaProperty.getBackgroundImpl());
        properties.put("kaptcha.background.clear.from", kaptchaProperty.getBackgroundClearFrom());
        properties.put("kaptcha.background.clear.to", kaptchaProperty.getBackgroundClearTo());
        properties.put("kaptcha.word.impl", kaptchaProperty.getWordImpl());
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
