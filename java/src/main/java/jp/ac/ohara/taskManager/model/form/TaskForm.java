package jp.ac.ohara.taskManager.model.form;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jp.ac.ohara.taskManager.config.messages.ErrorMessages;
import jp.ac.ohara.taskManager.model.Tag;
import jp.ac.ohara.taskManager.model.Task;
import lombok.Data;

@Data
public class TaskForm {

	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	private String name;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Future(message = ErrorMessages.FUTURE_MESSAGE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date due;

	private String tags;

	/**
	 * Taskモデルからformの値を設定
	 * @param Task task
	 */
	public void setTask(Task task, List<Tag> tags) {
		this.setName(task.getTitle());
		this.setDescription(task.getDescription());
		this.setDue(task.getDueDt());

		String tagNames = "";
		for (int i = 0; i < tags.size(); ++i) {
			if (i != 0) {
				tagNames += ",";
			}
			tagNames += tags.get(i).getName();
		}
		this.setTags(tagNames);
	}
}
