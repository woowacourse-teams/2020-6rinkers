package com.cocktailpick.back.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import com.cocktailpick.back.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {
	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	@WithMockUser(username = "toney", roles = "USER")
	void setUp(WebApplicationContext webApplicationContext) {
		User user = User.builder()
			.id(1L)
			.name("toney")
			.build();
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

		MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(springSecurity())
			.build();
	}

	@DisplayName("즐겨찾기를 추가한다.")
	@Test
	void addFavorite() throws Exception {
		when(userService.addFavorite(any(), any())).thenReturn(1L);

		mockMvc.perform(post("/api/user/me/cocktails")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new FavoriteRequest(1L)))
		)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/user/me/favorites/1"))
			.andDo(print());
	}
}