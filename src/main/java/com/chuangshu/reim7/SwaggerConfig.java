package com.chuangshu.reim7;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/9/23 10:43
 */
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//添加ApiOperiation注解的被扫描
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("校医院报销系统").description("swagger的API文档").version(" 1.0 ").build();
    }
}
