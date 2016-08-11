package ru.dyatel.karaka.config;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class KarakaConfigurationManagerImpl extends AbstractFileKarakaConfigurationManager {

	public KarakaConfigurationManagerImpl() {
		super(LogFactoryImpl.getLog(KarakaConfigurationManagerImpl.class));
	}

	@Override
	protected WritableResource getFileResource() {
		return new FileSystemResource("config.json");
	}

	@Override
	public KarakaConfiguration getDefaultConfig() {
		KarakaConfiguration config = KarakaConfiguration.getDefault();
		config.setWorkingDir(Paths.get("work"));
		return config;
	}

}
