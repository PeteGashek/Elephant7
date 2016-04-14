package ru.dyatel.karaka.config;

public interface KarakaConfigurationManager {

	KarakaConfiguration getConfig();

	void reload();

	void save();

}
