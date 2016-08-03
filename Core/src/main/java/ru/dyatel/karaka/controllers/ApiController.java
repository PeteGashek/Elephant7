package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.ApiResponse;
import ru.dyatel.karaka.boards.BoardCodeWrapper;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.threads.Post;
import ru.dyatel.karaka.threads.PostDao;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "/boards", method = RequestMethod.GET)
	public ApiResponse boardList() {
		return new ApiResponse(boardConfig.getBoards().keySet());
	}

	@RequestMapping(value = "/{boardCode}", method = RequestMethod.GET)
	public ApiResponse threadList(@Valid BoardCodeWrapper boardCode,
								  @RequestParam(required = false, defaultValue = "20") int count) {
		return new ApiResponse(ApiResponse.OK_CODE, postDb.getLatestThreads(boardCode.toString(), count));
	}

	@RequestMapping(value = "/{boardCode}/{threadId}", method = RequestMethod.GET)
	public ApiResponse postList(@Valid BoardCodeWrapper boardCode, @PathVariable long threadId,
								@RequestParam(required = false, defaultValue = "0") int count,
								@RequestParam(required = false, defaultValue = "0") int offset) {
		return new ApiResponse(ApiResponse.OK_CODE, postDb.getPosts(boardCode.toString(), threadId, count, offset));
	}

	@RequestMapping(value = "/{boardCode}/posts", method = RequestMethod.GET)
	public ApiResponse postListById(@Valid BoardCodeWrapper boardCode, String ids) {
		List<Long> idList = new ArrayList<>();
		for (String id : ids.split(",")) idList.add(Long.parseLong(id));
		return new ApiResponse(ApiResponse.OK_CODE, postDb.getPostsById(boardCode.toString(), idList));
	}

	@RequestMapping(value = "/{boardCode}/post", method = RequestMethod.POST)
	public ApiResponse createThread(@Valid BoardCodeWrapper boardCode, @Valid Post post) {
		post.setThreadId(0L);
		return post(boardCode, post);
	}

	@RequestMapping(value = "/{boardCode}/{threadId}/post", method = RequestMethod.POST)
	public ApiResponse post(@Valid BoardCodeWrapper boardCode, @Valid Post post) {
		postDb.post(boardCode.getBoardCode(), post);
		return ApiResponse.OK;
	}

}
