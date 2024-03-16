package jp.ac.ohara.taskManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.config.views.TopViews;
import jp.ac.ohara.taskManager.model.Task;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.service.TaskService;
import jp.ac.ohara.taskManager.service.TaskTagService;

@Controller
public class TopController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskTagService taskTagService;

	@GetMapping("/")
	public ModelAndView top(ModelAndView model, @AuthenticationPrincipal User loginUser) {
		// 認証情報を取得
		System.out.println("[TopController::top] login user ... " + loginUser);
		model.addObject("userTasks", this.taskService.getTasks(loginUser));
		model.setViewName(TopViews.TOP);
		return model;
	}

	@GetMapping("/detail/{id}")
	public ModelAndView detail(ModelAndView model, @PathVariable Long id, @AuthenticationPrincipal User loginUser) {
		Task task = this.taskService.getTask(id, loginUser);

		model.addObject("task", task);
		model.addObject("taskTags", this.taskTagService.getTasktags(task));
		model.addObject("pageName", "タスク詳細");

		model.setViewName(TopViews.DETAIL);
		return model;
	}

}
