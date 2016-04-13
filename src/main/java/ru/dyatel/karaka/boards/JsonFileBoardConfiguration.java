package ru.dyatel.karaka.boards;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.KarakaConfiguration;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileBoardConfiguration implements BoardConfiguration {

	private Log logger = LogFactoryImpl.getLog(JsonFileBoardConfiguration.class);

	private KarakaConfiguration config;

	private List<Board> boards = null;

	@Autowired
	public JsonFileBoardConfiguration(KarakaConfiguration config) {
		this.config = config;
		reload();
	}

	@Override
	public List<Board> getBoardList() {
		return boards;
	}

	private WritableResource getBoardConfig() {
		return new FileSystemResource(config.getWorkingDir().resolve("boards.json").toString());
	}

	@Override
	public void reload() {
		Resource boardConfig = getBoardConfig();
		try (Reader reader = new InputStreamReader(boardConfig.getInputStream())) {
			boards = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create()
					.fromJson(reader, new TypeToken<ArrayList<Board>>() {
					}.getType());
		} catch (Exception e) {
			logger.error("Failed to read board configuration from " + boardConfig, e);
		}
	}

	@Override
	public void save() {
		WritableResource boardConfig = getBoardConfig();
		try (Writer writer = new OutputStreamWriter(boardConfig.getOutputStream())) {
			new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.setPrettyPrinting()
					.create()
					.toJson(boards, writer);
		} catch (Exception e) {
			logger.error("Failed to save board configuration to " + boardConfig, e);
		}
	}

}