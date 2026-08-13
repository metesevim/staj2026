package com.metesevim.staj2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Staj2026Application {

	public static void main(String[] args) {
		SpringApplication.run(Staj2026Application.class, args);
	}

}
