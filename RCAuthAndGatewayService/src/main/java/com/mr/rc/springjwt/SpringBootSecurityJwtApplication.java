package com.mr.rc.springjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mr.rc.springjwt.swagger.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
//@EnableZuulProxy
//@EnableEurekaClient
@Import(SwaggerConfiguration.class)
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

}
