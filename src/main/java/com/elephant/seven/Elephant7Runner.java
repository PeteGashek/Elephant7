package com.elephant.seven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@PropertySource("application.properties")
@Import(SpringConfiguration.class)
public class Elephant7Runner {

	public static void main(String[] args) {
		SpringApplication.run(Elephant7Runner.class, args);
	}

}
