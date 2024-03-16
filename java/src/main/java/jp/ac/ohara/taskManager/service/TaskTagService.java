package jp.ac.ohara.taskManager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.Tag;
import jp.ac.ohara.taskManager.model.Task;
import jp.ac.ohara.taskManager.model.TaskTag;
import jp.ac.ohara.taskManager.repository.TagRepository;
import jp.ac.ohara.taskManager.repository.TaskTagRepository;

@Service
public class TaskTagService {

	@Autowired
	private TaskTagRepository taskTagRepository;
	@Autowired
	private TagRepository tagRepository;

	public void deleteTaskTag(Task task) {
		this.taskTagRepository.deleteByTaskIdEquals(task.getId());
	}

	public void save(Task task, List<Tag> tags) {
		for (Tag tag : tags) {
			this.save(task, tag);
		}
	}

	public void save(Task task, Tag tag) {
		TaskTag taskTag = new TaskTag();
		taskTag.setTaskId(task.getId());
		taskTag.setTagId(tag.getId());
		System.out.println("[TaskTagService::save] save record is " + taskTag);
		this.taskTagRepository.saveAndFlush(taskTag);
	}

	public List<Tag> getTasktags(Task task) {
		List<Tag> tags = new ArrayList<Tag>();
		List<TaskTag> taskTags = this.taskTagRepository.findAllByTaskId(task.getId());
		for (TaskTag taskTag : taskTags) {
			Tag tag = this.tagRepository.findById(taskTag.getTagId()).orElseThrow();
			tags.add(tag);
		}
		return tags;
	}
}
