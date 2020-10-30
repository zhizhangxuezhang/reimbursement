package com.chuangshu.reim7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.chuangshu.reim7.mapper"})
@EnableSwagger2
public class Reim7Application {

    public static void main(String[] args) {
        SpringApplication.run(Reim7Application.class, args);
    }

}
