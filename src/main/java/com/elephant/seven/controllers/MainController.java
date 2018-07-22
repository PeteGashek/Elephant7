package com.elephant.seven.controllers;

import com.elephant.seven.boards.BoardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private BoardConfiguration boardConfig;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("sections", boardConfig.getSections());
		model.addAttribute("boards", boardConfig.getBoards());
		return "index";
	}

}
