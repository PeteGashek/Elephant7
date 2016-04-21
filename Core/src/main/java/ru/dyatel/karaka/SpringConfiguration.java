package ru.dyatel.karaka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.dyatel.karaka.config.DbConnectionConfig;
import ru.dyatel.karaka.config.KarakaConfigurationManager;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ru.dyatel.karaka"})
public class SpringConfiguration {

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
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
