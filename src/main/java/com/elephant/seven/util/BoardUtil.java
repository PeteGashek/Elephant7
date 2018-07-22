package com.elephant.seven.util;

import com.elephant.seven.boards.Board;
import com.elephant.seven.boards.BoardConfiguration;
import com.elephant.seven.validation.BoardCodeValidator;

public class BoardUtil {

	public static Board validateAndGet(String boardCode, BoardConfiguration config, BoardCodeValidator validator) {
		validator.validate(boardCode);
		return config.getBoards().get(boardCode);
	}

}
