package ru.dyatel.karaka;

import java.nio.file.Path;

public interface KarakaConfiguration {

	Path getWorkingDir();

	void setWorkingDir(Path workingDir);

	void reload();

	void save();

}
