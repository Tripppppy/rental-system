package com.suke.RentalSystem;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@MapperScan("com.suke.RentalSystem.mapper")
@EnableTransactionManagement(proxyTargetClass = true)

public class Application {

    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}

