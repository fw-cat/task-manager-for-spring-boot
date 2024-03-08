package jp.ac.ohara.taskManager.config.email;

import java.net.URI;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import jp.ac.ohara.taskManager.controller.users.RegisterController;

public class PreRegister {
	public static final String SUBJECT = "【仮登録】タスク管理システム 仮登録メールです";

	public static String GetBody(String uuid) {
		UriComponents builder = MvcUriComponentsBuilder.fromMethodName(RegisterController.class, "register", uuid)
				.build();
		URI location = builder.toUri();

		return String.format("""
				タスク管理システムへの仮登録、ありがとうございます。
				下記URLにアクセスして、本登録を完了してください。
				（仮登録中は機能が制限されます）

				<a href="%s" target="_blank">本登録URL</a>
				""", location.toString());
	}
}
