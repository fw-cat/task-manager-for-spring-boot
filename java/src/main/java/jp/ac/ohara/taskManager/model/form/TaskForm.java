package jp.ac.ohara.taskManager.model.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jp.ac.ohara.taskManager.config.messages.ErrorMessages;
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
}
