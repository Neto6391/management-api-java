package com.example.company_service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

@ComponentScan("com.example.company_service")
@SpringBootApplication
public class CompanyServiceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		Map<String, Object> env = dotenv.entries().stream()
				.collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));

		SpringApplication app = new SpringApplication(CompanyServiceApplication.class);
		app.addInitializers(applicationContext -> {
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			environment.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", env));
		});

		app.run(args);
	}
}
