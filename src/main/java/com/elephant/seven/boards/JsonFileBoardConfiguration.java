package com.elephant.seven.boards;

import com.elephant.seven.config.KarakaConfigurationManager;
import com.elephant.seven.data.BoardTableManager;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JsonFileBoardConfiguration implements BoardConfiguration {

	private static class Config {

		@SerializedName("sections")
		public List<Section> sections = new ArrayList<>();

		@SerializedName("defaultBoard")
		public Board.DefaultConfig defaultBoard = new Board.DefaultConfig();

		@SerializedName("boardList")
		public List<Board> boardList = new ArrayList<>();

	}

	private static final String filename = "boards.json";

	private Log logger = LogFactoryImpl.getLog(JsonFileBoardConfiguration.class);

	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.create();

	private KarakaConfigurationManager karakaConfig;
	private BoardTableManager tableManager;

	private Config config;
	private Map<String, Board> boards = new HashMap<>();

	@Autowired
	public JsonFileBoardConfiguration(KarakaConfigurationManager karakaConfig, BoardTableManager tableManager) {
		this.karakaConfig = karakaConfig;
		this.tableManager = tableManager;
		reload();
	}

	@Override
	public List<Section> getSections() {
		return config.sections;
	}

	@Override
	public Map<String, Board> getBoards() {
		return boards;
	}

	private WritableResource getFileResource() {
		return new FileSystemResource(karakaConfig.getConfig().getWorkingDir().resolve(filename).toFile());
	}

	@Override
	public void reload() {
		Resource file = getFileResource();
		try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
			config = gson.fromJson(reader, Config.class);
			//---------------------------------------------------
			//TODO: Move this to test somewhere
//			Config config1 = new Config();
//
//			Section section = new Section();
//			section.setTitle("testTitle");
//			section.setBoards(Arrays.asList("Bord1"));
//
//			config1.sections.add(section);
//
//			Board board1 = new Board();
//			board1.setDefaultConfig(new Board.DefaultConfig());
//			board1.setCode("Bord1");
//			board1.setBumpLimit(1000);
//			board1.setDefaultUsername("SomeUserName");
//			board1.setDescription("SomeDescr");
//			board1.setMaxPages(1);
//			board1.setPostTable("PostTable1");
//			board1.setReadOnly(false);
//			board1.setThreadTable("ThreadTable1");
//
//
//
//			config1.boardList.add(board1);
//			Gson gson = new Gson();
//			String jsonInString = gson.toJson(config1);
			//----------------------------------------------------
			config.boardList.forEach(board -> {
				board.setDefaultConfig(config.defaultBoard);
				boards.put(board.getCode(), board);
			});
			config.boardList = null;

			logger.info("Successfully loaded board configuration from " + file);
		} catch (Exception e) {
			config = new Config();

			logger.error("Failed to read board configuration from " + file, e);
		}
		tableManager.prepareTables(boards.values());
	}

	@Override
	public void save() {
		WritableResource file = getFileResource();
		try (Writer writer = new OutputStreamWriter(file.getOutputStream(), StandardCharsets.UTF_8)) {
			config.boardList = boards.values().stream()
					.sorted(Comparator.comparing(Board::getCode))
					.collect(Collectors.toList());
			gson.toJson(config, writer);
			config.boardList = null;
		} catch (Exception e) {
			logger.error("Failed to save board configuration to " + file, e);
		}
	}

}
