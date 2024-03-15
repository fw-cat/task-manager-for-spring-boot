package jp.ac.ohara.taskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.ohara.taskManager.model.TaskTag;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {

	public Optional<TaskTag> findByTaskIdEqualsAndTagIdEquals(Long taskId, Long tagId);

	public void deleteByTaskIdEquals(Long taskId);
}
