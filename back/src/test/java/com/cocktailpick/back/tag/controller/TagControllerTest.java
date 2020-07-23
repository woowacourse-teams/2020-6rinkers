package com.cocktailpick.back.tag.controller;

import static com.cocktailpick.back.tag.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.cocktailpick.back.tag.service.TagService;

@WebMvcTest(controllers = {TagController.class})
class TagControllerTest {
	@MockBean
	private TagService tagService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}

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
			.andDo(print());
	}
}