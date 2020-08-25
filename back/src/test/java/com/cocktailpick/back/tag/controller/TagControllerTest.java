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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import com.cocktailpick.back.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.back.tag.docs.TagDocumentation;
import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.cocktailpick.back.tag.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TagController.class)
class TagControllerTest extends DocumentationWithSecurity {
	@MockBean
	private TagService tagService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(roles = "ADMIN")
	@DisplayName("태그 csv 파일을 저장한다.")
	@Test
	void addTagsByCsv() throws Exception {
		doNothing().when(tagService).saveAll(any());

		mockMvc.perform(multipart("/api/tags/upload/csv")
			.file(new MockMultipartFile("file", "test.csv", "text/csv",
				THREE_TAGS_CSV_CONTENT.getBytes()))
			.header("authorization", "Bearer ADMIN_TOKEN")
			.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/tags"))
			.andDo(print())
			.andDo(TagDocumentation.upload());
	}

	@DisplayName("조건에 맞는 태그를 조회한다.")
	@Test
	void findTags() throws Exception {
		TagResponse tagResponse1 = new TagResponse(1L, "탄산", "재료");
		TagResponse tagResponse2 = new TagResponse(2L, "초코", "재료");
		List<TagResponse> tagResponses = Arrays.asList(tagResponse1, tagResponse2);
		when(tagService.findTags(any(), any(), anyBoolean())).thenReturn(tagResponses);

		mockMvc.perform(get("/api/tags")
			.param("tagType", "INGREDIENT")
			.param("size", "2")
			.param("random", "false")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(TagDocumentation.findTags());
	}

	@WithMockUser(roles = "ADMIN")
	@DisplayName("태그를 생성한다.")
	@Test
	void createTag() throws Exception {
		TagRequest tagRequest = new TagRequest("새로운 태그", "CONCEPT");
		when(tagService.createTag(any())).thenReturn(1L);

		mockMvc.perform(post("/api/tags")
			.header("authorization", "Bearer ADMIN_TOKEN")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(tagRequest)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/tags/1"))
			.andDo(print())
			.andDo(TagDocumentation.create());
	}

	@WithMockUser(roles = "ADMIN")
	@DisplayName("태그를 수정한다.")
	@Test
	void update() throws Exception {
		TagRequest tagRequest = new TagRequest("update name", "CONCEPT");
		doNothing().when(tagService).update(anyLong(), any());

		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/tags/{id}", 1L)
			.header("authorization", "Bearer ADMIN_TOKEN")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(tagRequest)))
			.andExpect(status().isNoContent())
			.andDo(print()).andDo(TagDocumentation.update());
	}

	@WithMockUser(roles = "ADMIN")
	@DisplayName("태그를 삭제한다.")
	@Test
	void deleteTag() throws Exception {
		doNothing().when(tagService).delete(anyLong());

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/tags/{id}", 1L)
			.header("authorization", "Bearer ADMIN_TOKEN"))
			.andExpect(status().isNoContent())
			.andDo(print()).andDo(TagDocumentation.delete());
	}
}