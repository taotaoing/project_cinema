package com.stylefeng.guns.promo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@MapperScan("com.stylefeng.guns.promo.common.persistence.dao")
@EnableDubboConfiguration
public class GunsPromoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsPromoApplication.class, args);
    }
}
