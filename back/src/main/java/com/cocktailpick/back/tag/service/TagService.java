package com.cocktailpick.back.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class TagService {
	private final TagRepository tagRepository;

	@Transactional
	public void saveAll(MultipartFile file) {
		TagCsvReader tagCsvReader = new TagCsvReader(OpenCsvReader.from(file));
		List<Tag> tags = tagCsvReader.getTags();

		tagRepository.saveAll(tags);
	}

	@Transactional(readOnly = true)
	public List<TagResponse> findAllTags() {
		return TagResponse.listOf(tagRepository.findAll());
	}

	@Transactional
	public Long createTag(TagRequest tagRequest) {
		Tag tag = tagRepository.save(tagRequest.toTag());

		return tag.getId();
	}

	@Transactional
	public Tag update(Long id, TagRequest tagRequest) {
		Tag tag = tagRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
		tag.update(tagRequest.getName());

		return tag;
	}

	@Transactional
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}
}
