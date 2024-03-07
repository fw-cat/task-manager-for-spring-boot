package jp.ac.ohara.taskManager.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.RegistrationToken;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.RegisterForm;
import jp.ac.ohara.taskManager.repository.RegistrationTokenRepository;
import jp.ac.ohara.taskManager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegistrationTokenRepository registrationTokenRepository;
	@Autowired
	private MailtrapService mailtrapService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerUser(RegisterForm form) {
		this.registerUser(form.getUserName(), form.getMailAddress(), form.getPassword());
	}

	public void registerUser(String userName, String email, String password) {
		// ユーザの登録（仮）
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(this.passwordEncoder.encode(password));
		user.setStatus(1);
		this.userRepository.saveAndFlush(user);

		// 仮登録用コードの生成
		LocalDateTime expiredAt = LocalDateTime.now().plusWeeks(1);
		String uuid = UUID.randomUUID().toString();
		RegistrationToken token = new RegistrationToken();
		token.setUser(user);
		token.setExpiresAt(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()));
		token.setStatus(1);
		token.setToken(uuid);
		this.registrationTokenRepository.save(token);

		this.mailtrapService.sendEmail(email, "[仮登録メール]", "仮登録メールです. uuID :: " + uuid);
	}
}
