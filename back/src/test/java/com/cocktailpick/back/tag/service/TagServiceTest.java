package com.cocktailpick.back.tag.service;

import static com.cocktailpick.back.tag.Fixtures.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.tag.domain.TagType;
import com.cocktailpick.back.tag.dto.TagRequest;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
	private TagService tagService;

	@Mock
	private TagRepository tagRepository;

	@BeforeEach
	void setUp() {
		tagService = new TagService(tagRepository);
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
		Tag tag1 = new Tag("초코", TagType.FLAVOR);
		Tag tag2 = new Tag("탄산", TagType.INGREDIENT);
		when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

		tagService.findAllTags();

		verify(tagRepository).findAll();
	}

	@DisplayName("태그를 생성한다.")
	@Test
	void createTag() {
		TagRequest tagRequest = new TagRequest("새로운 태그", "CONCEPT");
		when(tagRepository.save(any())).thenReturn(tagRequest.toTag());

		tagService.createTag(tagRequest);

		verify(tagRepository).save(any());
	}
}