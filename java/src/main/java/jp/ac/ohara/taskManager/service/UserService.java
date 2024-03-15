package jp.ac.ohara.taskManager.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ac.ohara.taskManager.model.RegistrationToken;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.RegisterForm;
import jp.ac.ohara.taskManager.repository.RegistrationTokenRepository;
import jp.ac.ohara.taskManager.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegistrationTokenRepository registrationTokenRepository;
	@Autowired
	private MailSenderService mailtrapService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void preRegisterUser(RegisterForm form) {
		this.preRegisterUser(form.getUserName(), form.getMailAddress(), form.getPassword());
	}

	public void preRegisterUser(String userName, String email, String password) {
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

		this.mailtrapService.preRegister(email, uuid);
	}

	public void registerUser(String uuid) {
		RegistrationToken token = this.registrationTokenRepository.findByTokenEquals(uuid);
		User registerUser = token.getUser();
		registerUser.setStatus(2);
		this.userRepository.saveAndFlush(registerUser);
	}
}
