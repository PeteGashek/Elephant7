package com.elephant.seven.config;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class ElephantSevenConfigurationManagerImpl extends AbstractFileElephantSevenConfigurationManager {

	public ElephantSevenConfigurationManagerImpl() {
		super(LogFactoryImpl.getLog(ElephantSevenConfigurationManagerImpl.class));
	}

	@Override
	protected WritableResource getFileResource() {
		return new FileSystemResource("config.json");
	}

	@Override
	public ElephantSevenConfiguration getDefaultConfig() {
		ElephantSevenConfiguration config = ElephantSevenConfiguration.getDefault();
		config.setWorkingDir(Paths.get("work"));
		return config;
	}

}
