package com.elephant.seven.controllers;

import com.elephant.seven.boards.Board;
import com.elephant.seven.data.PostDao;
import com.elephant.seven.posts.Post;
import com.elephant.seven.posts.ThreadManager;
import com.elephant.seven.util.BoardUtil;
import com.elephant.seven.util.PostUtil;
import com.elephant.seven.validation.exceptions.EmptyPostException;
import com.elephant.seven.validation.exceptions.TooLongMessageException;
import com.elephant.seven.validation.exceptions.TooLongNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.elephant.seven.boards.BoardConfiguration;
import com.elephant.seven.util.Reference;
import com.elephant.seven.validation.BoardCodeValidator;
import com.elephant.seven.validation.ThreadIdValidator;

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
		model.addAttribute("sections", boardConfig.getSections());
		model.addAttribute("boards", boardConfig.getBoards());
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
		model.addAttribute("sections", boardConfig.getSections());
		model.addAttribute("boards", boardConfig.getBoards());
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
			return messageSource.getMessage("elephant.seven.send.failure.longname",
					new Object[]{Reference.MAX_NAME_LENGTH}, locale);
		if (e instanceof EmptyPostException)
			return messageSource.getMessage("elephant.seven.send.failure.empty", null, locale);
		if (e instanceof TooLongMessageException)
			return messageSource.getMessage("elephant.seven.send.failure.longmessage",
					new Object[]{Reference.MAX_MESSAGE_BYTE_LENGTH}, locale);
		return messageSource.getMessage("elephant.seven.send.failure.unknown", null, locale);
	}

}
