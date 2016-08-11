package ru.dyatel.karaka.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import ru.dyatel.karaka.util.GsonPathAdapter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public abstract class AbstractFileKarakaConfigurationManager implements KarakaConfigurationManager {

	protected Log logger;

	protected Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.registerTypeHierarchyAdapter(Path.class, new GsonPathAdapter().nullSafe())
			.serializeNulls()
			.create();

	protected KarakaConfiguration config;

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
		try (Reader reader = new InputStreamReader(configFile.getInputStream(), StandardCharsets.UTF_8)) {
			config = gson.fromJson(reader, KarakaConfiguration.class);
			logger.info("Successfully loaded Karaka config from " + configFile);
		} catch (Exception e) {
			logger.info("Failed to read Karaka config from " + configFile + ", using default", e);
			config = getDefaultConfig();
			save();
		}
	}

	@Override
	public void save() {
		WritableResource configFile = getFileResource();
		try {
			configFile.getFile().getParentFile().mkdirs();
		} catch (Exception e) {
		}
		try (Writer writer = new OutputStreamWriter(configFile.getOutputStream(), StandardCharsets.UTF_8)) {
			gson.toJson(config, writer);
		} catch (Exception e) {
			logger.error("Failed to save Karaka config to " + configFile, e);
		}
	}

}
