package com.cocktailpick.api.user.controller.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cocktailpick.api.common.WithMockCustomUser;
import com.cocktailpick.api.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.api.user.controller.UserController;
import com.cocktailpick.api.user.controller.docs.AuthDocumentation;
import com.cocktailpick.api.user.controller.docs.UserDocumentation;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.tag.dto.TagResponse;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.user.dto.FavoriteCocktailIdsResponse;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.dto.UserUpdateRequest;
import com.cocktailpick.core.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends DocumentationWithSecurity {
	@MockBean
	private UserService userService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);
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

	@DisplayName("즐겨찾기를 조회한다.")
	@WithMockCustomUser
	@Test
	void findFavorites() throws Exception {
		List<CocktailResponse> cocktailResponses = Arrays.asList(
			new CocktailResponse(1L, "싱가폴 슬링", "https://naver.com",
				Collections.singletonList(new TagResponse(1L, "마지막 양심", "컨셉"))),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net",
				Arrays.asList(new TagResponse(1L, "쫄깃쫄깃", "식감"), new TagResponse(2L, "짭쪼름", "맛")))
		);
		when(userService.findFavorites(any())).thenReturn(cocktailResponses);

		mockMvc.perform(get("/api/user/me/favorites")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("즐겨찾기한 칵테일의 id를 조회한다.")
	@WithMockCustomUser
	@Test
	void findFavoriteCocktailIdsTest() throws Exception {
		FavoriteCocktailIdsResponse favoriteCocktailIdsResponse = new FavoriteCocktailIdsResponse(
			Arrays.asList(1L, 2L, 3L));
		when(userService.findFavoriteCocktailIds(any())).thenReturn(favoriteCocktailIdsResponse);

		mockMvc.perform(get("/api/user/me/favoriteIds").accept(MediaType.APPLICATION_JSON)
			.header("authorization", "Bearer Token"))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(UserDocumentation.findFavoriteCocktailIds());
	}

	@DisplayName("즐겨찾기를 추가한다.")
	@WithMockCustomUser
	@Test
	void addFavorite() throws Exception {
		when(userService.addFavorite(any(), any())).thenReturn(1L);

		mockMvc.perform(post("/api/user/me/favorites")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new FavoriteRequest(1L)))
		)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/user/me/favorites/1"))
			.andDo(print());
	}

	@DisplayName("즐겨찾기를 삭제한다.")
	@WithMockCustomUser
	@Test
	void deleteFavorite() throws Exception {
		doNothing().when(userService).deleteFavorite(any(), anyLong());

		mockMvc.perform(delete("/api/user/me/favorites?cocktailId={cocktailId}", 1L))
			.andExpect(status().isNoContent())
			.andDo(print());
	}

	@DisplayName("사용자 정보를 수정한다.")
	@WithMockCustomUser
	@Test
	void updateCurrentUser() throws Exception {
		doNothing().when(userService).updateUser(any(), any());

		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");

		mockMvc.perform(patch("/api/user/me")
			.header("authorization", "Bearer ADMIN_TOKEN")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userUpdateRequest)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(UserDocumentation.updateUser());
	}

	@DisplayName("회원 탈퇴한다.")
	@WithMockCustomUser
	@Test
	void deleteCurrentUser() throws Exception {
		doNothing().when(userService).deleteCurrentUser(any());

		mockMvc.perform(delete("/api/user/me")
			.header("authorization", "Bearer ADMIN_TOKEN"))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(UserDocumentation.deleteMe());
	}

	@DisplayName("회원가입을 한다.")
	@Test
	void registerUser() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest("아이디", "a@email.com", "password");

		when(userService.registerUser(any())).thenReturn(1L);

		mockMvc.perform(post("/api/user/signup")
			.content(new ObjectMapper().writeValueAsBytes(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(AuthDocumentation.signup());
	}
}