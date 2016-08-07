package ru.dyatel.karaka.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dyatel.karaka.PageNotFoundException;

@Controller
public class MainController {

	@RequestMapping("/**")
	public void noMapping() {
		throw new PageNotFoundException();
	}

}
