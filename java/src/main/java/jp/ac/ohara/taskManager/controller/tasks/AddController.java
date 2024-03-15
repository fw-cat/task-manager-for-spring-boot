package jp.ac.ohara.taskManager.controller.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.config.views.TaskViews;
import jp.ac.ohara.taskManager.config.views.TopViews;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.TaskForm;
import jp.ac.ohara.taskManager.service.TagService;
import jp.ac.ohara.taskManager.service.TaskService;

@Controller
@RequestMapping("/tasks/add/")
public class AddController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TagService tagService;

	@GetMapping("/")
	public ModelAndView form(ModelAndView model, @AuthenticationPrincipal User loginUser, TaskForm taskForm) {
		System.out.println("[AddController::form] start .....");

		model.addObject("userTags", this.tagService.getTags(loginUser));
		model.addObject("taskForm", taskForm);
		model.addObject("formAction", "/tasks/add/");
		model.addObject("pageName", "タスクの追加");
		model.addObject("formName", "タスク追加フォーム");
		model.addObject("buttonText", "追加");

		model.setViewName(TaskViews.FORM);
		return model;
	}

	@PostMapping("/")
	public ModelAndView add(ModelAndView model, @AuthenticationPrincipal User loginUser,
			@ModelAttribute @Validated TaskForm taskForm, BindingResult result) {
		if (result.hasErrors()) {
			model.addObject("validError", result);
			return this.form(model, loginUser, taskForm);
		}
		try {
			this.taskService.save(taskForm, loginUser);
		} catch (Exception e) {
			model.addObject("validError", e.getMessage());
			return this.form(model, loginUser, taskForm);
		}
		model.setViewName(TopViews.TOP_REDIRECT);
		return model;
	}
}
