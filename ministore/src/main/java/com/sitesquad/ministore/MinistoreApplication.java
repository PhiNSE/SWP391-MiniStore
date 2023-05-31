package com.sitesquad.ministore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.sitesquad"})
@EnableScheduling
public class MinistoreApplication{

	public static void main(String[] args) {
		SpringApplication.run(MinistoreApplication.class, args);
	}

}
