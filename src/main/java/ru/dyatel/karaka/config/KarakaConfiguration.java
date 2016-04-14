package ru.dyatel.karaka.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class KarakaConfiguration {

	private Path workingDir = Paths.get("Karaka");

	public Path getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

}
