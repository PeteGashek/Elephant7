package ru.dyatel.karaka.boards;

import java.util.Map;

public interface BoardConfiguration {

	Map<String, Board> getBoards();

	void reload();

	void save();

}
