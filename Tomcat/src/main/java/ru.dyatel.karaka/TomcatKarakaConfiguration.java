package ru.dyatel.karaka;

import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class TomcatKarakaConfiguration implements KarakaConfiguration {

	private Log logger = LogFactoryImpl.getLog(TomcatKarakaConfiguration.class);

	private Path workingDir;

	@Autowired
	public TomcatKarakaConfiguration(ServletContext context) {
		try (Reader config = new InputStreamReader(
				new ServletContextResource(context, "config.json").getInputStream())) {
			workingDir = Paths.get(
					new JsonParser().parse(config)
							.getAsJsonObject().get("working_dir").getAsString());
		} catch (Exception e) {
			workingDir = Paths.get(System.getProperty("user.home")).resolve("Karaka");
		}
		logger.info("Setting working dir to " + workingDir);
	}

	@Override
	public Path getWorkingDir() {
		return workingDir;
	}

}
