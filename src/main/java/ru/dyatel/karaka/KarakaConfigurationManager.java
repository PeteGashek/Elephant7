package ru.dyatel.karaka;

public interface KarakaConfigurationManager {

	KarakaConfiguration getConfig();

	void reload();

	void save();

}
