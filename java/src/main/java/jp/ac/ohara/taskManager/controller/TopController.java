package jp.ac.ohara.taskManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopController {

	@GetMapping("/")
	public ModelAndView top(ModelAndView model) {
		model.setViewName("views/top");
		return model;
	}
}
