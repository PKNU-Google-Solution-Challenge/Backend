package com.mealPrep.mealPrep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MealPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealPrepApplication.class, args);
	}

}
