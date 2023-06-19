package com.sitesquad.ministore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.sitesquad"})
@EnableScheduling
@EnableWebMvc
public class MinistoreApplication{

	public static void main(String[] args) {
		SpringApplication.run(MinistoreApplication.class, args);
	}

}
