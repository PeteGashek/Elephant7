package com.elephant.seven.validation;

import com.elephant.seven.boards.Board;
import com.elephant.seven.posts.ThreadManager;
import com.elephant.seven.validation.exceptions.NotValidThreadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadIdValidator {

	@Autowired
	private ThreadManager threadManager;

	public void validate(Board board, long threadId) {
		if (threadId == 0) return;
		if (!threadManager.threadExists(board, threadId))
			throw new NotValidThreadException();
	}

}
