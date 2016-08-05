package ru.dyatel.karaka;

import com.google.gson.Gson;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.dyatel.karaka.config.DbConnectionConfig;
import ru.dyatel.karaka.config.KarakaConfigurationManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ru.dyatel.karaka"})
public class SpringConfiguration {

	@Bean
	public Gson gson() {
		return new Gson();
	}

	@Bean
	public DataSource dataSource(KarakaConfigurationManager configManager) {
		DbConnectionConfig config = configManager.getConfig().getDbConfig();

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(config.getDriver());
		dataSource.setUrl(config.getUrl());
		dataSource.setUsername(config.getUsername());
		dataSource.setPassword(config.getPassword());

		return dataSource;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("file:resources/i18n/text");
		messageSource.setDefaultEncoding("Windows-1251");
		return messageSource;
	}

}
