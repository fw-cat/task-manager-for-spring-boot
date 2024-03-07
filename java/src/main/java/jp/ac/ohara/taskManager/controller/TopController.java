package jp.ac.ohara.taskManager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.model.User;

@Controller
public class TopController {

	@GetMapping("/")
	public ModelAndView top(@AuthenticationPrincipal User loginUser, ModelAndView model) {
		// 認証情報を取得
		System.out.println("[TopController::top] login user ... " + loginUser.getEmail());
		model.setViewName("views/top");
		return model;
	}
}
