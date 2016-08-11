package ru.dyatel.karaka.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.posts.ThreadManager;
import ru.dyatel.karaka.validation.exceptions.NotValidThreadException;

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
