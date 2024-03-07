package jp.ac.ohara.taskManager.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.repository.UserRepository;

@Service
public class UserDetailsServiceImplt implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("serach name : " + username);
		User user = this.userRepository.findByEmailEquals(username);
		System.out.println(user.toString());
		return user;
	}
}
