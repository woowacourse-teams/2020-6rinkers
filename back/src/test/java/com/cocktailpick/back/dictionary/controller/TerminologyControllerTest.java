package com.cocktailpick.back.dictionary.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.web.context.WebApplicationContext;

import com.cocktailpick.back.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.back.dictionary.service.TerminologyService;
import com.cocktailpick.back.tag.dto.TerminologyRequest;
import com.cocktailpick.back.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TerminologyController.class)
class TerminologyControllerTest extends DocumentationWithSecurity {
	@MockBean
	private TerminologyService terminologyService;

	@MockBean
	private UserService userService;

	private TerminologyRequest terminologyRequest;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);

		terminologyRequest = TerminologyRequest.builder()
			.name("보드카")
			.terminologyType("술")
			.description("러시아의 술이고 감자나 호밀을 증류하여 만듭니다.")
			.imageUrl("https://media-verticommnetwork1.netdna-ssl.com/wines/absolut-vodka-45l-434781.jpg")
			.build();

		objectMapper = new ObjectMapper();

	}

	@DisplayName("용어를 생성한다.")
	@Test
	void save() throws Exception {
		when(terminologyService.save(any())).thenReturn(1L);

		mockMvc.perform(post("/api/terminologies")
			.content(objectMapper.writeValueAsString(terminologyRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/terminologies/1"));
	}
}