package ru.dyatel.karaka;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
			config = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.registerTypeAdapter(new TypeToken<Path>() {
					}.getType(), new GsonPathAdapter())
					.create()
					.fromJson(reader, KarakaConfiguration.class);
			logger.info("Successfully loaded Karaka config from " + configFile);
		} catch (Exception e) {
			config = new KarakaConfiguration();
			logger.info("Failed to read Karaka config from " + configFile + ", using default", e);
		}
	}

	@Override
	public void save() {
		WritableResource configFile = getFileResource();
		try (Writer writer = new OutputStreamWriter(configFile.getOutputStream())) {
			new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.setPrettyPrinting()
					.registerTypeAdapter(new TypeToken<Path>() {
					}.getType(), new GsonPathAdapter())
					.create()
					.toJson(config, writer);
		} catch (IOException e) {
			logger.error("Failed to save Karaka config to " + configFile, e);
		}
	}

}
