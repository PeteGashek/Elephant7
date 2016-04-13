package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class TomcatKarakaConfiguration extends AbstractFileKarakaConfiguration {

	private static String filename = "config.json";

	public TomcatKarakaConfiguration() {
		super(LogFactoryImpl.getLog(TomcatKarakaConfiguration.class));
	}

	@Override
	protected WritableResource getConfig() {
		String catalinaBase = System.getProperty("catalina.base");
		if (catalinaBase == null) catalinaBase = System.getenv("CATALINA_BASE");

		if (catalinaBase != null)
			return new FileSystemResource(Paths.get(catalinaBase, "work/Karaka/" + filename).toFile());

		return new FileSystemResource("Karaka/" + filename);
	}

}
