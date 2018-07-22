package com.elephant.seven.config;

public interface ElephantSevenConfigurationManager {

	ElephantSevenConfiguration getConfig();

	ElephantSevenConfiguration getDefaultConfig();

	void reload();

	void save();

}
