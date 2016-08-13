package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dyatel.karaka.boards.Board;
import ru.dyatel.karaka.boards.BoardConfiguration;
import ru.dyatel.karaka.data.PostDao;
import ru.dyatel.karaka.posts.Post;
import ru.dyatel.karaka.posts.ThreadManager;
import ru.dyatel.karaka.util.BoardUtil;
import ru.dyatel.karaka.util.PostUtil;
import ru.dyatel.karaka.util.Reference;
import ru.dyatel.karaka.validation.BoardCodeValidator;
import ru.dyatel.karaka.validation.ThreadIdValidator;
import ru.dyatel.karaka.validation.exceptions.EmptyPostException;
import ru.dyatel.karaka.validation.exceptions.TooLongNameException;

import java.util.List;
import java.util.Locale;

@Controller
public class BoardController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ApiController api;

	@Autowired
	private BoardConfiguration boardConfig;

	@Autowired
	private BoardCodeValidator boardValidator;
	@Autowired
	private ThreadIdValidator threadIdValidator;

	@Autowired
	private PostDao postDb;

	@Autowired
	private ThreadManager threadManager;

	@RequestMapping(value = "/{boardCode:(?!api|static).*$}", method = RequestMethod.GET)
	public String threadList(@PathVariable String boardCode,
							 @RequestParam(required = false, defaultValue = "0") int page, Model model) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		List<Long> threadIds = threadManager.getLatestThreads(board, 10, page * 10);
		List<Post> threads = postDb.getPostsById(board, threadIds);
		PostUtil.sortByThreadIdList(threads, threadIds);
		model.addAttribute("threads", threads);
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("currentPage", page);
		model.addAttribute("pages", Math.ceil(threadManager.getThreadCount(board) / 10f));
		return "board";
	}

	@RequestMapping(value = "/{boardCode:(?!api|static).*$}/{threadId:\\d+$}", method = RequestMethod.GET)
	public String thread(@PathVariable String boardCode,
						 @PathVariable long threadId, Model model) {
		Board board = BoardUtil.validateAndGet(boardCode, boardConfig, boardValidator);
		threadIdValidator.validate(board, threadId);
		model.addAttribute("posts", postDb.getPosts(board, threadId, 0, 0));
		model.addAttribute("boardCode", boardCode);
		model.addAttribute("threadId", threadId);
		return "thread";
	}

	@RequestMapping(value = "/{boardCode:(?!api|static).*$}/post", method = RequestMethod.POST)
	public String createThread(@PathVariable String boardCode, Post post, Model model, Locale locale) {
		try {
			return "redirect:/" + boardCode + "/" + api.createThread(boardCode, post).getMessage();
		} catch (TooLongNameException | EmptyPostException e) {
			model.addAttribute("error", getError(e, locale));
			model.addAttribute("backLink", boardCode);
			return "error/post_error";
		}
	}

	@RequestMapping(value = "/{boardCode:(?!api|static).*$}/{threadId:\\d+$}/post", method = RequestMethod.POST)
	public String post(@PathVariable String boardCode,
					   @PathVariable long threadId, Post post, Model model, Locale locale) {
		try {
			api.post(boardCode, post);
			return "redirect:/" + boardCode + "/" + threadId;
		} catch (TooLongNameException | EmptyPostException e) {
			model.addAttribute("error", getError(e, locale));
			model.addAttribute("backLink", boardCode + "/" + threadId);
			return "error/post_error";
		}
	}

	private String getError(Exception e, Locale locale) {
		if (e instanceof TooLongNameException)
			return messageSource.getMessage("karaka.send.failure.longname",
					new Object[]{Reference.MAX_NAME_LENGTH}, locale);
		if (e instanceof EmptyPostException)
			return messageSource.getMessage("karaka.send.failure.empty", null, locale);
		return messageSource.getMessage("karaka.send.failure.unknown", null, locale);
	}

}
