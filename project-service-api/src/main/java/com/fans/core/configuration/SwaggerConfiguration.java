package com.fans.core.configuration;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * className: SwaggerConfiguration
 *
 * @author k
 * @version 1.0
 * @description Swagger配置
 * @date 2018-12-20 11:40
 **/
@Component
@Configuration
@EnableSwagger2
@Data
@ConfigurationProperties(prefix = "swagger" )
@Slf4j
public class SwaggerConfiguration {
    //原路径访问地址：http://localhost:8080/swagger-ui.html
    //新版ui路径访问地址：http://localhost:8080/doc.html
    //paramType：表示参数放在哪个地方
    //header-->请求参数的获取：@RequestHeader(代码中接收注解)
    //query-->请求参数的获取：@RequestParam(代码中接收注解)
    //path（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
    //body-->请求参数的获取：@RequestBody(代码中接收注解)
    //form（不常用）
    /**
     * 授予开关权限
     */
    private boolean enable = false;
    /**
     * 当前文档的标题
     */
    private String title = "Kapok RestFul System";
    /**
     * 当前文档的详细描述
     */
    private String description = "poweredByBy-Health";
    /**
     * 服务接口源码地址
     */
    private String serviceUrl = "https://github.com";
    /**
     * 联系方式
     */
    private Contact contact = new Contact("kapok" , serviceUrl, "5219824@qq.com" );

    @Bean
    public Docket createRestApiV1() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(title)
                .contact(contact)
                .description(description)
                .termsOfServiceUrl(serviceUrl)
                .version("1.0" )
                .build();
        //noinspection Guava
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo)
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(Predicates.or(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)))
                //加了RestController注解的类，生成接口文档
                //.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApiV2() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(title)
                .contact(contact)
                .description(description)
                .termsOfServiceUrl(serviceUrl)
                .version("2.0" )
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("版本-2.0" )
                .enable(enable)
                .apiInfo(apiInfo)
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //加了RestController注解的类，生成接口文档
                //.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .apis(RequestHandlerSelectors.basePackage("com.fans.api.v2" ))
                .paths(PathSelectors.any())
                .build();
    }
}
