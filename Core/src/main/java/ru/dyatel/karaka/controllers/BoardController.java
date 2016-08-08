package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dyatel.karaka.data.PostDao;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.posts.ThreadManager;
import ru.dyatel.karaka.util.PostUtil;
import ru.dyatel.karaka.validation.BoardCodeValidator;
import ru.dyatel.karaka.validation.ThreadIdValidator;

import java.util.List;

@Controller
public class BoardController {

	@Autowired
	private BoardCodeValidator boardValidator;
	@Autowired
	private ThreadIdValidator threadIdValidator;

	@Autowired
	private PostDao postDb;

	@Autowired
	private ThreadManager threadManager;

	@RequestMapping("/{boardCode:(?!api|static).*$}")
	public String threadList(@PathVariable String boardCode,
							 @RequestParam(required = false, defaultValue = "0") int page, Model model) {
		boardValidator.validate(boardCode);
		List<Long> threadIds = threadManager.getLatestThreads(boardCode, 10, page * 10);
		List<Post> threads = postDb.getPostsById(boardCode, threadIds);
		PostUtil.sortByThreadIdList(threads, threadIds);
		model.addAttribute("threads", threads);
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("currentPage", page);
		model.addAttribute("pages", Math.ceil(threadManager.getThreadCount(boardCode) / 10));
		return "board";
	}

	@RequestMapping("/{boardCode:(?!api|static).*$}/{threadId}")
	public String thread(@PathVariable String boardCode,
						 @PathVariable long threadId, Model model) {
		boardValidator.validate(boardCode);
		threadIdValidator.validate(boardCode, threadId);
		model.addAttribute("posts", postDb.getPosts(boardCode, threadId, 0, 0));
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("threadId", threadId);
		return "thread";
	}

}
