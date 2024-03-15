package jp.ac.ohara.taskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.ohara.taskManager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	public List<Task> findAllByUserId(Long userId);

}
