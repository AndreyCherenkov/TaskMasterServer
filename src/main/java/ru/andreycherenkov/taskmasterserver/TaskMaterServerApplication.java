package ru.andreycherenkov.taskmasterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskMaterServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMaterServerApplication.class, args);
	}

}
