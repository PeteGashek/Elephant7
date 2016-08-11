package ru.dyatel.karaka.boards;

import java.util.List;
import java.util.Map;

public interface BoardConfiguration {

	List<Section> getSections();

	Map<String, Board> getBoards();

	void reload();

	void save();

}
