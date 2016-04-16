package ru.dyatel.karaka.config;

import java.nio.file.Path;

public class KarakaConfiguration {

	public static KarakaConfiguration getDefault() {
		KarakaConfiguration config = new KarakaConfiguration();
		return config;
	}

	private Path workingDir = null;

	public Path getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

}
