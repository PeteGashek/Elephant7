package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.config.AbstractFileKarakaConfigurationManager;
import ru.dyatel.karaka.config.KarakaConfiguration;

import java.nio.file.Paths;

@Component
public class StandaloneKarakaConfigurationManager extends AbstractFileKarakaConfigurationManager {

	public StandaloneKarakaConfigurationManager() {
		super(LogFactoryImpl.getLog(StandaloneKarakaConfigurationManager.class));
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
