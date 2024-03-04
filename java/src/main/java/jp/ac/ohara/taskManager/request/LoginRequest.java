package jp.ac.ohara.taskManager.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String mailAddress;
	private String password;
}
