package com.elephant.seven.config;

public interface KarakaConfigurationManager {

	KarakaConfiguration getConfig();

	KarakaConfiguration getDefaultConfig();

	void reload();

	void save();

}
