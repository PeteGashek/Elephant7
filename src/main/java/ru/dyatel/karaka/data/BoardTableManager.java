package ru.dyatel.karaka.data;

import ru.dyatel.karaka.boards.Board;

import java.util.Map;

public interface BoardTableManager {

	void prepareTables(Map<String, Board> boards);

}
