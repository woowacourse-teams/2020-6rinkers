package com.cocktailpick.back.tag.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.domain.TagRepository;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
	private TagService tagService;

	public static final String CONTENT = "태그\n두강\n두중\n두약";

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
			CONTENT.getBytes());

		tagService.saveAll(file);

		verify(tagRepository).saveAll(anyCollection());
	}
}