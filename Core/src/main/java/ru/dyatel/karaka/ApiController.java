package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.boards.BoardCodeWrapper;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.threads.Post;
import ru.dyatel.karaka.threads.PostDao;
import ru.dyatel.karaka.util.ResponseCode;

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
		try {
			postDb.createThread(boardCode.getBoardCode(), post);
		} catch (Exception e) {
			LogFactoryImpl.getLog(this.getClass()).error("Got exception while handling new thread request", e);
			return new ApiResponse(ResponseCode.INTERNAL_ERROR, "Failed to create a thread, try later");
		}

		return new ApiResponse(ResponseCode.OK);
	}

	@RequestMapping(value = "/{boardName}/{threadId}/post", method = RequestMethod.POST)
	public ApiResponse post(@Valid BoardCodeWrapper boardCode, @Valid Post post) {
		try {
			postDb.post(boardCode.getBoardCode(), post);
		} catch (Exception e) {
			LogFactoryImpl.getLog(this.getClass()).error("Got exception while handling post request", e);
			return new ApiResponse(ResponseCode.INTERNAL_ERROR, "Failed to post a message, try later");
		}

		return new ApiResponse(ResponseCode.OK);
	}

}
