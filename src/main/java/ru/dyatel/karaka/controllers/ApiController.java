package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dyatel.karaka.ApiResponse;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.data.PostDao;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.posts.PostType;
import ru.dyatel.karaka.posts.ThreadManager;
import ru.dyatel.karaka.util.BoardUtil;
import ru.dyatel.karaka.validation.BoardCodeValidator;
import ru.dyatel.karaka.validation.PostValidator;
import ru.dyatel.karaka.validation.ThreadIdValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private BoardConfiguration boardConfig;

	@Autowired
	private BoardCodeValidator boardValidator;
	@Autowired
	private ThreadIdValidator threadIdValidator;
	@Autowired
	private PostValidator postValidator;

	@Autowired
	private PostDao postDb;

	@Autowired
	private ThreadManager threadManager;

	@RequestMapping("/test")
	public String test() {
		return "Hello, World!";
	}

	@RequestMapping(value = "/boards", method = RequestMethod.GET)
	public ApiResponse boardList() {
		return new ApiResponse(boardConfig.getSections());
	}

	@RequestMapping(value = "/{boardCode}", method = RequestMethod.GET)
	public ApiResponse threadList(@PathVariable String boardCode,
								  @RequestParam(required = false, defaultValue = "20") int count) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		return new ApiResponse(threadManager.getLatestThreads(board, count, 0));
	}

	@RequestMapping(value = "/{boardCode}/{threadId}", method = RequestMethod.GET)
	public ApiResponse postList(@PathVariable String boardCode, @PathVariable long threadId,
								@RequestParam(required = false, defaultValue = "0") int count,
								@RequestParam(required = false, defaultValue = "0") int offset) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		threadIdValidator.validate(board, threadId);
		return new ApiResponse(postDb.getPosts(board, threadId, count, offset));
	}

	@RequestMapping(value = "/{boardCode}/posts", method = RequestMethod.GET)
	public ApiResponse postListById(@PathVariable String boardCode, String ids) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		List<Long> idList = new ArrayList<>();
		for (String id : ids.split(",")) idList.add(Long.parseLong(id));
		return new ApiResponse(postDb.getPostsById(board, idList));
	}

	@RequestMapping(value = "/{boardCode}/post", method = RequestMethod.POST)
	public ApiResponse createThread(@PathVariable String boardCode, Post post) {
		post.setThreadId(0L);
		post.setType(PostType.OP);
		return post(boardCode, post);
	}

	@RequestMapping(value = "/{boardCode}/{threadId}/post", method = RequestMethod.POST)
	public ApiResponse post(@PathVariable String boardCode, Post post) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		threadIdValidator.validate(board, post.getThreadId());
		postValidator.validate(post);

		postDb.post(board, post);
		return new ApiResponse(post.getPostId());
	}

}
