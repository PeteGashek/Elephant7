package com.elephant.seven.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.elephant.seven.boards.BoardConfiguration;
import com.elephant.seven.validation.exceptions.NotValidBoardCodeException;

@Component
public class BoardCodeValidator {

	@Autowired
	private BoardConfiguration boardConfig;

	public void validate(String board) {
		if (!boardConfig.getBoards().containsKey(board))
			throw new NotValidBoardCodeException();
	}

}
