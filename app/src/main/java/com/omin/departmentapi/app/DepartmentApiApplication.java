package com.omin.departmentapi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.omin.departmentapi")
@EntityScan(basePackages = "com.omin.departmentapi")
@EnableJpaRepositories(basePackages = "com.omin.departmentapi")
public class DepartmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApiApplication.class, args);
	}

}
