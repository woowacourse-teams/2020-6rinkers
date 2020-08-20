package com.cocktailpick.back.tag.service;

import static com.cocktailpick.back.tag.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.domain.CocktailTagRepository;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.tag.domain.TagType;
import com.cocktailpick.back.tag.dto.TagRequest;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
	private TagService tagService;

	@Mock
	private TagRepository tagRepository;

	@Mock
	private CocktailTagRepository cocktailTagRepository;

	@BeforeEach
	void setUp() {
		tagService = new TagService(tagRepository, cocktailTagRepository);
	}

	@DisplayName("태그 csv 파일을 저장한다.")
	@Test
	void saveAll() {
		MultipartFile file = new MockMultipartFile("file", "태그.csv", "text/csv",
			THREE_TAGS_CSV_CONTENT.getBytes());

		tagService.saveAll(file);

		verify(tagRepository).saveAll(anyCollection());
	}

	@DisplayName("모든 태그를 조회한다.")
	@Test
	void findAllTags() {
		Tag tag1 = Tag.builder().name("초코").tagType(TagType.FLAVOR).build();
		Tag tag2 = Tag.builder().name("탄산").tagType(TagType.INGREDIENT).build();
		when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

		tagService.findTags(null, null, false);

		verify(tagRepository).findAll();
	}

	@DisplayName("타입에 맞는 태그를 조회한다.")
	@Test
	void findTagsByTagType() {
		Tag tag1 = Tag.builder().name("초코").tagType(TagType.FLAVOR).build();
		Tag tag2 = Tag.builder().name("탄산").tagType(TagType.INGREDIENT).build();
		when(tagRepository.findByTagType(TagType.INGREDIENT)).thenReturn(Collections.singletonList(tag2));

		tagService.findTags(TagType.INGREDIENT, null, false);

		verify(tagRepository).findByTagType(TagType.INGREDIENT);
	}

	@DisplayName("지정한 수만큼 태그를 조회한다.")
	@Test
	void findTags() {
		Tag tag1 = Tag.builder().name("초코").tagType(TagType.FLAVOR).build();
		Tag tag2 = Tag.builder().name("탄산").tagType(TagType.INGREDIENT).build();
		when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

		assertThat(tagService.findTags(null, 1, false)).hasSize(1);
	}

	@DisplayName("태그를 생성한다.")
	@Test
	void createTag() {
		TagRequest tagRequest = new TagRequest("새로운 태그", "CONCEPT");
		when(tagRepository.save(any())).thenReturn(tagRequest.toTag());

		tagService.createTag(tagRequest);

		verify(tagRepository).save(any());
	}

	@DisplayName("태그를 수정한다.")
	@Test
	void update() {
		Tag tag = Tag.builder().id(1L).name("Before Tag").tagType(TagType.CONCEPT).build();
		TagRequest tagRequest = new TagRequest("After Tag", "CONCEPT");
		when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

		tagService.update(1L, tagRequest);
		assertThat(tag.getName()).isEqualTo(tagRequest.getName());
	}

	@DisplayName("태그를 삭제한다.")
	@Test
	void delete() {
		tagService.delete(1L);

		verify(tagRepository).deleteById(1L);
	}
}