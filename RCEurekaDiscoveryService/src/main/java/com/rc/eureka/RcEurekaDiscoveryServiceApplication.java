package com.rc.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RcEurekaDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcEurekaDiscoveryServiceApplication.class, args);
	}

}
