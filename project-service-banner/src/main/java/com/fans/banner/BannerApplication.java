package com.fans.banner;

import com.fans.common.constant.ServiceConstants;
import com.rule.MyRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.fans"})
@EnableAsync
@EnableEurekaClient
@EnableFeignClients({"com.fans"})
@EnableHystrix
//配置指定自定义的ribbon规则 方式一 （规则类不能在maven的groupId包下）
@RibbonClients(value = {
        @RibbonClient(value = ServiceConstants.SERVICE_USER, configuration = MyRule.class)
})
@Slf4j
public class BannerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BannerApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        Boolean isOpenSwagger = environment.getProperty("swagger.enable" , Boolean.class);
        String port = environment.getProperty("server.port" );
        String path = environment.getProperty("server.servlet.context-path" );
        if (StringUtils.isNotBlank(path) && path.length() == 1) {
            path = StringUtils.EMPTY;
        }
        if (isOpenSwagger != null && isOpenSwagger) {
            log.info("--> swagger url is http://localhost:{}{}/doc.html" ,
                    StringUtils.isNotBlank(port) ? port : "8080" ,
                    path);
        }
    }

}
