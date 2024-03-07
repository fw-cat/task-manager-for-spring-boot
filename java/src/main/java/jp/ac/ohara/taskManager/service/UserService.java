package jp.ac.ohara.taskManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.model.form.RegisterForm;
import jp.ac.ohara.taskManager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerUser(RegisterForm form) {
		this.registerUser(form.getUserName(), form.getMailAddress(), form.getPassword());
	}

	public void registerUser(String userName, String email, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(this.passwordEncoder.encode(password));
		user.setStatus(1);
		this.userRepository.saveAndFlush(user);
	}
}
