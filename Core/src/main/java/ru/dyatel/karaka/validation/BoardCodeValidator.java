package ru.dyatel.karaka.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.boards.BoardConfiguration;

@Component
public class BoardCodeValidator {

	@Autowired
	private BoardConfiguration boardConfig;

	public void validate(String board) {
		if (!boardConfig.getBoards().containsKey(board))
			throw new NotValidBoardCodeException();
	}

}
