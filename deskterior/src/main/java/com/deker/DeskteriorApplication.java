package com.deker;

import com.deker.security.SecurityAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DeskteriorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskteriorApplication.class, args);
	}

}
