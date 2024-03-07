package jp.ac.ohara.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.ohara.taskManager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmailEquals(String email);

}
