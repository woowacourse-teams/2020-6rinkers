package com.cocktailpick.back.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cocktailpick.back.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.back.common.WithMockCustomUser;
import com.cocktailpick.back.user.docs.UserDocumentation;
import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.dto.UserResponse;
import com.cocktailpick.back.user.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends DocumentationWithSecurity {
	@MockBean
	private UserService userService;

	@DisplayName("내 정보를 조회한다.")
	@WithMockCustomUser
	@Test
	void getCurrentUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("hi");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");

		when(userService.findMe(anyLong())).thenReturn(UserResponse.of(user));

		mockMvc.perform(get("/api/user/me")
			.header("authorization", "Bearer USER_TOKEN"))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(UserDocumentation.findMe());
	}
}