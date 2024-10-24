package ru.andreycherenkov.taskmasterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class TaskMasterServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMasterServerApplication.class, args);
	}

}
