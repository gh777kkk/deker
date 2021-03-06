package com.deker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DeskteriorApplication {
	public static void main(String[] args) {

		SpringApplication.run(DeskteriorApplication.class, args);
	}

}
