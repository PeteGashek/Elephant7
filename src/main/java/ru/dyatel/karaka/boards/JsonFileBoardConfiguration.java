package ru.dyatel.karaka.boards;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.config.KarakaConfigurationManager;
import ru.dyatel.karaka.data.BoardTableManager;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonFileBoardConfiguration implements BoardConfiguration {

	private static final String filename = "boards.json";

	private Log logger = LogFactoryImpl.getLog(JsonFileBoardConfiguration.class);

	private KarakaConfigurationManager config;
	private BoardTableManager tableManager;

	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.create();

	private Map<String, Board> boards;

	@Autowired
	public JsonFileBoardConfiguration(KarakaConfigurationManager config, BoardTableManager tableManager) {
		this.config = config;
		this.tableManager = tableManager;
		reload();
	}

	@Override
	public Map<String, Board> getBoards() {
		return boards;
	}

	private WritableResource getBoardConfig() {
		return new FileSystemResource(config.getConfig().getWorkingDir().resolve(filename).toFile());
	}

	@Override
	public void reload() {
		Resource boardConfig = getBoardConfig();
		try (Reader reader = new InputStreamReader(boardConfig.getInputStream(), StandardCharsets.UTF_8)) {
			boards = gson.fromJson(reader, new TypeToken<HashMap<String, Board>>() {
			}.getType());
			logger.info("Successfully loaded board configuration from " + boardConfig);
		} catch (Exception e) {
			logger.error("Failed to read board configuration from " + boardConfig, e);
			boards = new HashMap<>();
		}
		tableManager.prepareTables(boards);
	}

	@Override
	public void save() {
		WritableResource boardConfig = getBoardConfig();
		try (Writer writer = new OutputStreamWriter(boardConfig.getOutputStream(), StandardCharsets.UTF_8)) {
			gson.toJson(boards, writer);
		} catch (Exception e) {
			logger.error("Failed to save board configuration to " + boardConfig, e);
		}
	}

}
