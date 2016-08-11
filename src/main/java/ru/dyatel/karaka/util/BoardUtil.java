package ru.dyatel.karaka.util;

import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.validation.BoardCodeValidator;

public class BoardUtil {

	public static Board validateAndGet(String boardCode, BoardConfiguration config, BoardCodeValidator validator) {
		validator.validate(boardCode);
		return config.getBoards().get(boardCode);
	}

}
