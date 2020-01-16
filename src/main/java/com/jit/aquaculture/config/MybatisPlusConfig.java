package com.jit.aquaculture.config;

import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }
}
