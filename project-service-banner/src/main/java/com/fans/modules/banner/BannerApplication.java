package com.fans.modules.banner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fans"})
public class BannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BannerApplication.class, args);
    }

}
