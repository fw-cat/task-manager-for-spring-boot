package jp.ac.ohara.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.ohara.taskManager.model.RegistrationToken;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

	public RegistrationToken findByTokenEquals(String token);

}
