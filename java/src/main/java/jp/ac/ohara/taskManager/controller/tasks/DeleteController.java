package jp.ac.ohara.taskManager.controller.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.config.views.TaskViews;
import jp.ac.ohara.taskManager.config.views.TopViews;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.service.TaskService;

@Controller
@RequestMapping("/tasks/delete/")
public class DeleteController {
	@Autowired
	private TaskService taskService;

	@GetMapping("/{id}")
	public ModelAndView confirm(ModelAndView model, @PathVariable Long id, @AuthenticationPrincipal User loginUser) {
		model.addObject("task", this.taskService.getTask(id, loginUser));
		model.setViewName(TaskViews.DELETE_CONFIRM);
		return model;
	}

	@PostMapping("/{id}")
	public ModelAndView delete(ModelAndView model, @PathVariable Long id, @AuthenticationPrincipal User loginUser) {
		try {
			this.taskService.deleteTask(id, loginUser);
		} catch (Exception e) {
			model.addObject("validError", e.getMessage());
			this.confirm(model, id, loginUser);
		}
		model.setViewName(TopViews.TOP_REDIRECT);
		return model;
	}
}
