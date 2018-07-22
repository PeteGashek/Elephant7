package com.elephant.seven.config;

import java.nio.file.Path;

public class ElephantSevenConfiguration {

	public static ElephantSevenConfiguration getDefault() {
		ElephantSevenConfiguration config = new ElephantSevenConfiguration();
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
