package jp.ac.ohara.taskManager.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.model.form.RegisterForm;
import jp.ac.ohara.taskManager.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@GetMapping("/register/")
	public ModelAndView index(RegisterForm registerForm, ModelAndView model) {
		model.addObject("registerForm", registerForm);
		model.setViewName("views/login/register");
		return model;
	}

	@PostMapping("/register/")
	public ModelAndView preRegister(@ModelAttribute @Validated RegisterForm registerForm,
			BindingResult result, ModelAndView model) {
		if (result.hasErrors()) {
			return this.index(registerForm, model);
		}

		try {
			this.userService.preRegisterUser(registerForm);

		} catch (Exception e) {
			return this.index(registerForm, model);
		}
		model.setViewName("redirect:/");
		return model;
	}

	@GetMapping("/register/{uuid}")
	public String register(@PathVariable @NonNull String uuid) {
		this.userService.registerUser(uuid);
		return "redirect:/";
	}
}
