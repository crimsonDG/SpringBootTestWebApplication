package com.core.client.config;

import feign.Request;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class AuthFeignConfig {

    @Bean
    public Request.Options authFeignOptions(){
        int connectTimeoutMillis = 5000;
        int readTimeoutMillis = 5000;
        return new Request.Options(connectTimeoutMillis, TimeUnit.MILLISECONDS, readTimeoutMillis, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public AuthFeignErrorDecoder authFeignErrorDecoder(){
        return new AuthFeignErrorDecoder();
    }

}
