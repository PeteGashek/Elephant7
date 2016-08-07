package ru.dyatel.karaka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dyatel.karaka.boards.BoardConfiguration;

@Controller
public class MainController {

	@Autowired
	private BoardConfiguration boardConfig;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("boards", boardConfig.getBoards().keySet());
		return "index";
	}

}
