package ru.dyatel.karaka;

import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.threads.Post;
import ru.dyatel.karaka.threads.PostDao;
import ru.dyatel.karaka.util.ResponseCode;

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
	public ApiResponse createThread(@PathVariable String boardName, Post post) {
		if (!boardConfig.getBoards().containsKey(boardName))
			return new ApiResponse(ResponseCode.NO_SUCH_BOARD, "Board \"" + boardName + "\" doesn't exist");
		if (StringUtils.isEmpty(post.getMessage()))
			return new ApiResponse(ResponseCode.EMPTY_MESSAGE, "Message is empty");

		try {
			postDb.createThread(boardName, post);
		} catch (Exception e) {
			LogFactoryImpl.getLog(this.getClass()).error("Got exception while handling new thread request", e);
			return new ApiResponse(ResponseCode.INTERNAL_ERROR, "Failed to create a thread, try later");
		}

		return new ApiResponse(ResponseCode.OK);
	}

	@RequestMapping(value = "/{boardName}/{threadId}/post", method = RequestMethod.POST)
	public ApiResponse post(@PathVariable String boardName, Post post) {
		if (!boardConfig.getBoards().containsKey(boardName))
			return new ApiResponse(ResponseCode.NO_SUCH_BOARD, "Board \"" + boardName + "\" doesn't exist");
		if (StringUtils.isEmpty(post.getMessage()))
			return new ApiResponse(ResponseCode.EMPTY_MESSAGE, "Message is empty");

		try {
			postDb.post(boardName, post);
		} catch (Exception e) {
			LogFactoryImpl.getLog(this.getClass()).error("Got exception while handling post request", e);
			return new ApiResponse(ResponseCode.INTERNAL_ERROR, "Failed to post a message, try later");
		}

		return new ApiResponse(ResponseCode.OK);
	}

}
