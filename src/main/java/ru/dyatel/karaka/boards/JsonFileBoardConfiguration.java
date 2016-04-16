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

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileBoardConfiguration implements BoardConfiguration {

	private static final String filename = "boards.json";

	private Log logger = LogFactoryImpl.getLog(JsonFileBoardConfiguration.class);

	private KarakaConfigurationManager config;

	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.create();

	private List<Board> boards;

	@Autowired
	public JsonFileBoardConfiguration(KarakaConfigurationManager config) {
		this.config = config;
		reload();
	}

	@Override
	public List<Board> getBoardList() {
		return boards;
	}

	private WritableResource getBoardConfig() {
		return new FileSystemResource(config.getConfig().getWorkingDir().resolve(filename).toFile());
	}

	@Override
	public void reload() {
		Resource boardConfig = getBoardConfig();
		try (Reader reader = new InputStreamReader(boardConfig.getInputStream())) {
			boards = gson.fromJson(reader, new TypeToken<ArrayList<Board>>() {
			}.getType());
			logger.info("Successfully loaded board configuration from " + boardConfig);
		} catch (Exception e) {
			logger.error("Failed to read board configuration from " + boardConfig, e);
			boards = new ArrayList<>();
		}
	}

	@Override
	public void save() {
		WritableResource boardConfig = getBoardConfig();
		try (Writer writer = new OutputStreamWriter(boardConfig.getOutputStream())) {
			gson.toJson(boards, writer);
		} catch (Exception e) {
			logger.error("Failed to save board configuration to " + boardConfig, e);
		}
	}

}
