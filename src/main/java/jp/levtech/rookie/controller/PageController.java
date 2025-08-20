package jp.levtech.rookie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

	@GetMapping("/main")
	public String mainPage() {
		return "main";
	}

	@GetMapping(path = "/{page}")
	public String page(@PathVariable String page) {
		return page;
	}
}
