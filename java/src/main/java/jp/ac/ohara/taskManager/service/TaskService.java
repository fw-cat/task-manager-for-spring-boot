package jp.ac.ohara.taskManager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.Tag;
import jp.ac.ohara.taskManager.model.Task;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.TaskForm;
import jp.ac.ohara.taskManager.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TagService tagService;
	@Autowired
	private TaskTagService taskTagService;

	/**
	 * TaskFormモデルからのデータの保存
	 * @param TaskForm form
	 * @param User user
	 */
	public void save(TaskForm form, User user) {
		System.out.println("[TaskService::save] start ....");

		// タグの追加
		String[] tagsName = form.getTags().split(",");
		List<Tag> tags = this.tagService.saveTags(tagsName, user);
		System.out.println("[TaskService::save] save tags is " + tags.toString());

		// タスクの追加
		Task task = new Task();
		task.setTitle(form.getName());
		task.setDescription(form.getDescription());
		task.setUser(user);
		task.setDueDt(form.getDue());
		task.setStatus(1);
		this.save(task, tags);
		System.out.println("[TaskService::save] save task is " + task);

		// タスクに紐づくタグを一旦削除
		this.taskTagService.deleteTaskTag(task);
		// タスクに紐づくタグを一括登録
		this.taskTagService.save(task, tags);
	}

	public void save(TaskForm form, User user, Long id) {
		System.out.println("[TaskService::save] start ....");

		// タグの追加
		String[] tagsName = form.getTags().split(",");
		List<Tag> tags = this.tagService.saveTags(tagsName, user);
		System.out.println("[TaskService::save] save tags is " + tags.toString());

		// タスクの追加
		Task task = new Task();
		task.setId(id);
		task.setTitle(form.getName());
		task.setDescription(form.getDescription());
		task.setUser(user);
		task.setDueDt(form.getDue());
		task.setStatus(1);
		this.save(task, tags);
		System.out.println("[TaskService::save] save task is " + task);

		// タスクに紐づくタグを一旦削除
		this.taskTagService.deleteTaskTag(task);
		// タスクに紐づくタグを一括登録
		this.taskTagService.save(task, tags);
	}

	/**
	 * データの保存
	 * @param Task task
	 */
	public void save(Task task, List<Tag> tags) {
		this.taskRepository.saveAndFlush(task);
	}

	/**
	 * 対象ユーザに紐づくタスク一覧を取得
	 * @param user
	 * @return
	 */
	public List<Task> getTasks(User user) {
		List<Task> tasks = this.taskRepository.findAllByUserId(user.getId());
		return tasks;
	}

	/**
	 * 対象ユーザに紐づくタスクを取得
	 * @param Long index
	 * @param User user
	 * @return
	 */
	public Task getTask(Long index, User user) {
		Task task = this.taskRepository.findAllByIdAndUserId(index, user.getId());
		return task;
	}

	public void deleteTask(Long id, User user) {
		Task task = this.getTask(id, user);
		Date nowAt = new Date();
		task.setId(id);
		task.setDeletedAt(nowAt);

		this.taskRepository.saveAndFlush(task);
	}
}
