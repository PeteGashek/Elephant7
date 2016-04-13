package ru.dyatel.karaka.boards;

import java.util.List;

public interface BoardConfiguration {

	List<Board> getBoardList();

	void reload();

	void save();

}
