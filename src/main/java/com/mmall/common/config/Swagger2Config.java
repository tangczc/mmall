package com.mmall.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 接口文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createAppRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiwei.server.controller.wx"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createWebRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后端")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiwei.server.controller.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("太极八卦--API说明文档")
                .description("2019年1月15日--开发版本")
                .version("1.0")
                .build();
    }

}