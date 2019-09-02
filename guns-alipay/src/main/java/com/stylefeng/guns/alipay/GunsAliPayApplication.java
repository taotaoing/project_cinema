package com.stylefeng.guns.alipay;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@MapperScan("com.stylefeng.guns.user.common.persistence.dao")
@EnableDubboConfiguration
public class GunsAliPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsAliPayApplication.class, args);
    }
}
