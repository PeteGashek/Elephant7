package ru.dyatel.karaka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
@Import(SpringConfiguration.class)
public class KarakaRunner {

	public static void main(String[] args) {
		SpringApplication.run(KarakaRunner.class, args);
	}

}
