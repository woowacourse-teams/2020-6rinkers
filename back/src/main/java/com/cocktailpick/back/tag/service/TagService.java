package com.cocktailpick.back.tag.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.CocktailTagRepository;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.tag.domain.TagType;
import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class TagService {
	private final TagRepository tagRepository;

	private final CocktailTagRepository cocktailTagRepository;

	@Transactional
	public void saveAll(MultipartFile file) {
		TagCsvReader tagCsvReader = new TagCsvReader(OpenCsvReader.from(file));
		List<Tag> tags = tagCsvReader.getTags();

		tagRepository.saveAll(tags);
	}

	@Transactional(readOnly = true)
	public List<TagResponse> findTags(TagType tagType, Integer size, boolean random) {
		List<Tag> tags = Optional.ofNullable(tagType)
			.map(tagRepository::findByTagType)
			.orElseGet(tagRepository::findAll);
		if (random) {
			Collections.shuffle(tags);
		}
		return TagResponse.listOf(findTagsBySize(tags, size));
	}

	private List<Tag> findTagsBySize(List<Tag> tags, Integer size) {
		if (Objects.isNull(size) || isSizeOver(tags, size)) {
			return tags;
		}
		return tags.subList(0, size);
	}

	private boolean isSizeOver(List<Tag> tags, Integer size) {
		return size >= tags.size();
	}

	@Transactional
	public Long createTag(TagRequest tagRequest) {
		Tag tag = tagRepository.save(tagRequest.toTag());

		return tag.getId();
	}

	@Transactional
	public void update(Long id, TagRequest tagRequest) {
		Tag tag = tagRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
		tag.update(tagRequest.getName(), TagType.of(tagRequest.getTagType()));
	}

	@Transactional
	public void delete(Long id) {
		List<CocktailTag> cocktailTags = cocktailTagRepository.findByTagId(id);
		for (CocktailTag cocktailTag : cocktailTags) {
			cocktailTag.setTag(null);
			cocktailTag.getCocktail().deleteCocktailTag(cocktailTag);
		}

		tagRepository.deleteById(id);
	}
}
