package jp.ac.ohara.taskManager.controller.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.config.views.LoginViews;
import jp.ac.ohara.taskManager.model.form.LoginForm;

@Controller
public class LoginController {

	@GetMapping("/login/")
	public ModelAndView form(ModelAndView model, LoginForm form) {
		System.out.println("[LoginController::form] start");
		model.addObject("loginForm", form);
		model.setViewName(LoginViews.LOGIN_FORM);
		return model;
	}

	@PostMapping("/login/")
	public ModelAndView login(ModelAndView model, @ModelAttribute LoginForm form) {
		System.out.println("[LoginController::login] start");
		model.addObject("loginForm", form);
		model.setViewName(LoginViews.LOGIN_FORM);
		return model;
	}
}
