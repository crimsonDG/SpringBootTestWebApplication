package com.sba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@ComponentScan({"com.management.security"})
public class SpringBootAdminApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }
}
