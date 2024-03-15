package jp.ac.ohara.taskManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.ohara.taskManager.model.Tag;
import jp.ac.ohara.taskManager.model.User;
import jp.ac.ohara.taskManager.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;

	public List<Tag> getTags(User user) {
		List<Tag> tags = this.tagRepository.findAllByUserId(user.getId());
		return tags;
	}

	public List<Tag> saveTags(String[] tagsName, User user) {
		List<Tag> saveTags = new ArrayList<Tag>();
		for (String _tag : tagsName) {
			System.out.println("[TaskService::save] save tag name is " + _tag);
			Tag tag = this.save(_tag, user);
			saveTags.add(tag);
			System.out.println("[TaskService::save] save tag is " + tag);
		}
		return saveTags;
	}

	public Tag save(String tagName, User user) {
		// ユーザIDに紐づくタグを取得
		Optional<Tag> tag = this.tagRepository.findByNameEqualsAndUserIdEquals(tagName, user.getId());
		if (tag.isPresent()) {
			// 検索できたタグを返す
			return tag.get();
		} else {
			// 新規作成する
			Tag newTag = new Tag();
			newTag.setName(tagName);
			newTag.setUser(user);
			this.tagRepository.saveAndFlush(newTag);
			return newTag;
		}
	}
}
