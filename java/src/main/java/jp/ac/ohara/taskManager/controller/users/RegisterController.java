package jp.ac.ohara.taskManager.controller.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

	@GetMapping("/register/")
	public ModelAndView index(ModelAndView model) {
		model.setViewName("views/login/register");
		return model;
	}
}
