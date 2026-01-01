package com.example.productmanagement.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis設定
 */
@Configuration
@MapperScan("com.example.productmanagement.mapper")
public class MyBatisConfig {
}

