package com.fans.modules.banner;

import com.fans.modules.constant.ServiceConstants;
import com.rule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
public class BannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BannerApplication.class, args);
    }

}
