package ru.dyatel.karaka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.boards.BoardCodeWrapper;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.threads.Post;
import ru.dyatel.karaka.threads.PostDao;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private BoardConfiguration boardConfig;

	@Autowired
	private PostDao postDb;

	@RequestMapping("/test")
	public String test() {
		return "Hello, World!";
	}

	@RequestMapping(value = "/{boardName}/post", method = RequestMethod.POST)
	public ApiResponse createThread(@Valid BoardCodeWrapper boardCode, @Valid Post post) {
		postDb.createThread(boardCode.getBoardCode(), post);
		return ApiResponse.OK;
	}

	@RequestMapping(value = "/{boardName}/{threadId}/post", method = RequestMethod.POST)
	public ApiResponse post(@Valid BoardCodeWrapper boardCode, @Valid Post post) {
		postDb.post(boardCode.getBoardCode(), post);
		return ApiResponse.OK;
	}

}
