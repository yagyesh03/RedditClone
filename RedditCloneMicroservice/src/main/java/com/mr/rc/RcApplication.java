package com.mr.rc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.mr.rc.swagger.SwaggerConfiguration;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class RcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcApplication.class, args);
	}

}
