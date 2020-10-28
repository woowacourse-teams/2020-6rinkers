package com.cocktailpick.core.tag.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.cocktailpick.core.tag.domain.CocktailTag;
import com.cocktailpick.core.tag.domain.CocktailTagRepository;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagRepository;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.tag.dto.TagRequest;
import com.cocktailpick.core.tag.dto.TagResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class TagService {
	private final TagRepository tagRepository;

	private final CocktailTagRepository cocktailTagRepository;

	@Transactional
	public void saveAll(List<TagRequest> tagRequests) {
		tagRepository.saveAll(tagRequests.stream().map(TagRequest::toTag).collect(Collectors.toList()));
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
		validateTag(tagRequest);
		Tag tag = tagRepository.save(tagRequest.toTag());

		return tag.getId();
	}

	@Transactional
	public void update(Long id, TagRequest tagRequest) {
		validateTag(tagRequest);
		Tag tag = tagRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.TAG_NOT_FOUND));

		tag.update(tagRequest.getName(), TagType.of(tagRequest.getTagType()));
	}

	private void validateTag(TagRequest tagRequest) {
		boolean isPresent = tagRepository.findByName(tagRequest.getName()).isPresent();

		if (isPresent) {
			throw new InvalidValueException(ErrorCode.TAG_DUPLICATED);
		}
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
