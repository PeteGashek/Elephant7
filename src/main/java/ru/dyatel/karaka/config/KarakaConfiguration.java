package ru.dyatel.karaka.config;

import java.nio.file.Path;

public class KarakaConfiguration {

	public static KarakaConfiguration getDefault() {
		KarakaConfiguration config = new KarakaConfiguration();
		return config;
	}

	private Path workingDir = null;
	private DbConnectionConfig dbConfig = new DbConnectionConfig();

	public Path getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

	public DbConnectionConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DbConnectionConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

}
