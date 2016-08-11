package ru.dyatel.karaka.config;

public interface KarakaConfigurationManager {

	KarakaConfiguration getConfig();

	KarakaConfiguration getDefaultConfig();

	void reload();

	void save();

}
