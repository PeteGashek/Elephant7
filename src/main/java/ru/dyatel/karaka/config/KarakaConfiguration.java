package ru.dyatel.karaka.config;

import java.nio.file.Path;

public class KarakaConfiguration {

	private Path workingDir = null;

	public Path getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

}
