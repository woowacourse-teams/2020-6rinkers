package com.cocktailpick.back.tag.controller;

import static com.cocktailpick.back.tag.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.cocktailpick.back.common.documentation.Documentation;
import com.cocktailpick.back.tag.docs.TagDocumentation;
import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.cocktailpick.back.tag.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {TagController.class})
class TagControllerTest extends Documentation {
	@MockBean
	private TagService tagService;

	@DisplayName("태그 csv 파일을 저장한다.")
	@Test
	void addTagsByCsv() throws Exception {
		doNothing().when(tagService).saveAll(any());

		mockMvc.perform(multipart("/api/tags/upload/csv")
			.file(new MockMultipartFile("file", "test.csv", "text/csv",
				THREE_TAGS_CSV_CONTENT.getBytes()))
			.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/tags"))
			.andDo(print())
			.andDo(TagDocumentation.upload());
	}

	@DisplayName("모든 태그를 조회한다.")
	@Test
	void findAllTags() throws Exception {
		TagResponse tagResponse1 = new TagResponse("탄산");
		TagResponse tagResponse2 = new TagResponse("초코");
		List<TagResponse> tagResponses = Arrays.asList(tagResponse1, tagResponse2);
		when(tagService.findAllTags()).thenReturn(tagResponses);

		mockMvc.perform(get("/api/tags")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(TagDocumentation.findTags());
	}

	@DisplayName("태그를 생성한다.")
	@Test
	void createTag() throws Exception {
		TagRequest tagRequest = new TagRequest("새로운 태그");
		when(tagService.createTag(any())).thenReturn(1L);

		mockMvc.perform(post("/api/tags")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(tagRequest)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/tags/1"))
			.andDo(print())
			.andDo(TagDocumentation.create());
	}
}