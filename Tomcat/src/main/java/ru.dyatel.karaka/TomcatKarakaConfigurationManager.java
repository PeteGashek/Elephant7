package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.config.AbstractFileKarakaConfigurationManager;
import ru.dyatel.karaka.config.KarakaConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class TomcatKarakaConfigurationManager extends AbstractFileKarakaConfigurationManager {

	private static final String filename = "config.json";

	public TomcatKarakaConfigurationManager() {
		super(LogFactoryImpl.getLog(TomcatKarakaConfigurationManager.class));
	}

	private Path resolveWorkingDir() {
		String catalinaBase = System.getProperty("catalina.base");
		if (catalinaBase == null) catalinaBase = System.getenv("CATALINA_BASE");

		if (catalinaBase != null)
			return Paths.get(catalinaBase, "work/Karaka/");

		return Paths.get("Karaka/");
	}

	@Override
	public KarakaConfiguration getDefaultConfig() {
		KarakaConfiguration config = KarakaConfiguration.getDefault();
		config.setWorkingDir(resolveWorkingDir());
		return config;
	}

	@Override
	protected WritableResource getFileResource() {
		return new FileSystemResource(resolveWorkingDir().resolve(filename).toFile());
	}

}
