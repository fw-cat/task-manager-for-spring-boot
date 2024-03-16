package jp.ac.ohara.taskManager.controller.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.taskManager.config.views.TaskViews;
import jp.ac.ohara.taskManager.config.views.TopViews;
import jp.ac.ohara.taskManager.model.Tag;
import jp.ac.ohara.taskManager.model.Task;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.TaskForm;
import jp.ac.ohara.taskManager.service.TagService;
import jp.ac.ohara.taskManager.service.TaskService;
import jp.ac.ohara.taskManager.service.TaskTagService;

@Controller
@RequestMapping("/tasks/edit/")
public class EditController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TagService tagService;

	@Autowired
	private TaskTagService taskTagService;

	@GetMapping("/{id}")
	public ModelAndView form(ModelAndView model, @PathVariable Long id, @AuthenticationPrincipal User loginUser,
			TaskForm taskForm) {
		System.out.println("[AddController::form] start .....");
		Task task = this.taskService.getTask(id, loginUser);
		List<Tag> tags = this.taskTagService.getTasktags(task);
		taskForm.setTask(task, tags);

		model.addObject("userTags", this.tagService.getTags(loginUser));
		model.addObject("taskForm", taskForm);
		model.addObject("formAction", "/tasks/edit/" + id);
		model.addObject("pageName", "タスクの編集");
		model.addObject("formName", "タスク編集フォーム");
		model.addObject("buttonText", "更新");

		model.setViewName(TaskViews.FORM);
		return model;
	}

	@PostMapping("/{id}")
	public ModelAndView add(ModelAndView model, @PathVariable Long id, @AuthenticationPrincipal User loginUser,
			@ModelAttribute @Validated TaskForm taskForm, BindingResult result) {
		if (result.hasErrors()) {
			model.addObject("validError", result);
			return this.form(model, id, loginUser, taskForm);
		}
		try {
			this.taskService.save(taskForm, loginUser, id);
		} catch (Exception e) {
			model.addObject("validError", e.getMessage());
			return this.form(model, id, loginUser, taskForm);
		}
		model.setViewName(TopViews.TOP_REDIRECT);
		return model;
	}
}
