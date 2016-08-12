package ru.dyatel.karaka.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.dyatel.karaka.config.DbConnectionConfig;
import ru.dyatel.karaka.config.KarakaConfigurationManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DbConfiguration {

	@Bean
	public DataSource dataSource(KarakaConfigurationManager configManager) {
		DbConnectionConfig config = configManager.getConfig().getDbConfig();

		Properties properties = new Properties();
		properties.setProperty("characterEncoding", "utf8");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(config.getDriver());
		dataSource.setUrl(config.getUrl());
		dataSource.setUsername(config.getUsername());
		dataSource.setPassword(config.getPassword());
		dataSource.setConnectionProperties(properties);

		return dataSource;
	}

}
