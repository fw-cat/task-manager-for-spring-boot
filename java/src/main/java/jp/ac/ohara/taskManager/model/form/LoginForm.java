package jp.ac.ohara.taskManager.model.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jp.ac.ohara.taskManager.config.messages.ErrorMessages;
import lombok.Data;

@Data
public class LoginForm {
	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	@Email(message = ErrorMessages.EMAIL)
	private String mailAddress;

	@NotBlank(message = ErrorMessages.BLANK_MESSAGE)
	private String password;
}
