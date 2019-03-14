package com.suke.RentalSystem;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@MapperScan("com.suke.RentalSystem.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
public class Application {

    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        // 添加数据源
        factory.setDataSource(dataSource);

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        return factory.getObject();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}

