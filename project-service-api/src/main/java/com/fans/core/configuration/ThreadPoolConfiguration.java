package com.fans.core.configuration;

import com.fans.common.threadpool.basic.PoolRegister;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fans.common.utils.ReflectUtils.getMavenModel;

/**
 * className: ThreadPoolConfiguration
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2018-12-20 11:40
 **/
@Configuration
@Slf4j
public class ThreadPoolConfiguration {
    @Bean(name = "poolRegister")
    public PoolRegister<?> poolRegister() {
        return new PoolRegister<>();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            List<String> beanNameList = Lists.newArrayList(beanNames);
            Model mavenModel = getMavenModel();
            beanNameList = beanNameList.stream().filter(beanName -> {
                String groupId = mavenModel.getGroupId();
                if (StringUtils.isBlank(groupId)) {
                    groupId = mavenModel.getParent() == null ? StringUtils.EMPTY : mavenModel.getParent().getGroupId();
                }
                return applicationContext.getBean(beanName).getClass().getName().contains(groupId);
            }).collect(Collectors.toList());
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            beanNameList.forEach(beanName -> {
                Object bean = applicationContext.getBean(beanName);
                builder.put(beanName, bean.getClass().getName());
            });
            ImmutableMap<String, String> map = builder.build();
//            log.info("\r\n--> Let's inspect the beans provided by Spring Boot to project: \r\n{}", JsonUtils.obj2FormattingString(map));
        };
    }
}
