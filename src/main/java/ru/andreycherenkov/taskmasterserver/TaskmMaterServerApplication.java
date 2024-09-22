package ru.andreycherenkov.taskmasterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskmMaterServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmMaterServerApplication.class, args);
	}

}
