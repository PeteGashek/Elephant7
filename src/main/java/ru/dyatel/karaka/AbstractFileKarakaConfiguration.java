package ru.dyatel.karaka;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.logging.Log;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractFileKarakaConfiguration implements KarakaConfiguration {

	protected Log logger;

	protected Path workingDir;

	public AbstractFileKarakaConfiguration(Log logger) {
		this.logger = logger;
		reload();
	}

	@Override
	public Path getWorkingDir() {
		return workingDir;
	}

	@Override
	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

	protected abstract WritableResource getConfig();

	@Override
	public void reload() {
		Resource config = getConfig();
		try (Reader reader = new InputStreamReader(config.getInputStream())) {
			workingDir = Paths.get(
					new JsonParser().parse(reader)
							.getAsJsonObject().get("working_dir").getAsString());
		} catch (Exception e) {
			workingDir = Paths.get("Karaka");
		}
		logger.info("Setting working dir to " + workingDir);
	}

	@Override
	public void save() {
		WritableResource config = getConfig();
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(config.getOutputStream()))) {
			writer.beginObject()
					.name("working_dir").value(workingDir.toString())
					.endObject();
		} catch (IOException e) {
			logger.error("Failed to save Karaka config to " + config, e);
		}
	}

}
