package ru.dyatel.karaka.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import ru.dyatel.karaka.util.GsonPathAdapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public abstract class AbstractFileKarakaConfigurationManager implements KarakaConfigurationManager {

	protected Log logger;

	protected Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.registerTypeHierarchyAdapter(Path.class, new GsonPathAdapter())
			.create();

	protected KarakaConfiguration config = null;

	public AbstractFileKarakaConfigurationManager(Log logger) {
		this.logger = logger;
		reload();
	}

	@Override
	public KarakaConfiguration getConfig() {
		return config;
	}

	protected abstract WritableResource getFileResource();

	@Override
	public void reload() {
		Resource configFile = getFileResource();
		try (Reader reader = new InputStreamReader(configFile.getInputStream())) {
			config = gson.fromJson(reader, KarakaConfiguration.class);
			logger.info("Successfully loaded Karaka config from " + configFile);
		} catch (Exception e) {
			config = new KarakaConfiguration();
			logger.info("Failed to read Karaka config from " + configFile + ", using default", e);
			save();
		}
	}

	@Override
	public void save() {
		WritableResource configFile = getFileResource();
		try (Writer writer = new OutputStreamWriter(configFile.getOutputStream())) {
			gson.toJson(config, writer);
		} catch (IOException e) {
			logger.error("Failed to save Karaka config to " + configFile, e);
		}
	}

}
