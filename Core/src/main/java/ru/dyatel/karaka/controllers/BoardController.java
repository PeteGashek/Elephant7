package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dyatel.karaka.boards.BoardCodeWrapper;
import ru.dyatel.karaka.threads.Post;
import ru.dyatel.karaka.threads.PostDao;
import ru.dyatel.karaka.util.PostUtil;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {

	@Autowired
	private PostDao postDb;

	@RequestMapping("/{boardCode:(?!api|static).*$}")
	public String threadList(@Valid BoardCodeWrapper boardCode,
							 @RequestParam(required = false, defaultValue = "0") int page, Model model) {
		List<Post> threads = postDb.getPostsById(boardCode.toString(), threadIds);
		PostUtil.sortByThreadIdList(threads, threadIds);
		model.addAttribute("threads", threads);
		model.addAttribute("boardCode", boardCode.toString());
		return "board";
	}

	@RequestMapping("/{boardCode:(?!api|static).*$}/{threadId}")
	public String thread(@Valid BoardCodeWrapper boardCode, @PathVariable long threadId, Model model) {
		model.addAttribute("posts", postDb.getPosts(boardCode.toString(), threadId, 0, 0));
		model.addAttribute("boardCode", boardCode.toString());
		return "thread";
	}

}
