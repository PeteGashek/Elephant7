package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.config.AbstractFileKarakaConfigurationManager;

import java.nio.file.Paths;

@Component
public class TomcatKarakaConfigurationManager extends AbstractFileKarakaConfigurationManager {

	private static final String filename = "config.json";

	public TomcatKarakaConfigurationManager() {
		super(LogFactoryImpl.getLog(TomcatKarakaConfigurationManager.class));
	}

	@Override
	protected WritableResource getFileResource() {
		String catalinaBase = System.getProperty("catalina.base");
		if (catalinaBase == null) catalinaBase = System.getenv("CATALINA_BASE");

		if (catalinaBase != null)
			return new FileSystemResource(Paths.get(catalinaBase, "work/Karaka/" + filename).toFile());

		return new FileSystemResource("Karaka/" + filename);
	}

}
