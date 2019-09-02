package com.stylefeng.guns.gateway;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@MapperScan("com.stylefeng.guns.gateway.common.persistence.dao")
@EnableDubboConfiguration
public class GunsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsGatewayApplication.class, args);
    }
}
