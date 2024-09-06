package com.petrov.databases;

import org.springframework.boot.SpringApplication;

public class TestCourseworkApplication {

	public static void main(String[] args) {
		SpringApplication.from(CourseworkApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
