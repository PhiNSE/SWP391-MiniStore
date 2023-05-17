package com.sitesquad.ministore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sitesquad"})
public class MinistoreApplication{

	public static void main(String[] args) {
		SpringApplication.run(MinistoreApplication.class, args);
	}

}
