package jp.ac.ohara.taskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.ohara.taskManager.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	public List<Tag> findAllByUserId(Long userId);

	public Optional<Tag> findByNameEqualsAndUserIdEquals(String name, Long userId);
}
