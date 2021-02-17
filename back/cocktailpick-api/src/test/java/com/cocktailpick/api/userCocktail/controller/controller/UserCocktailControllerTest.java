package com.cocktailpick.api.userCocktail.controller.controller;

import com.cocktailpick.api.common.WithMockCustomUser;
import com.cocktailpick.api.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.api.userCocktail.controller.UserCocktailController;
import com.cocktailpick.api.userCocktail.controller.docs.UserCocktailDocumentation;
import com.cocktailpick.core.usercocktail.dto.UserCocktailCreateRequest;
import com.cocktailpick.core.usercocktail.service.UserCocktailService;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserCocktailController.class)
class UserCocktailControllerTest extends DocumentationWithSecurity {
    @MockBean
    private UserCocktailService userCocktailService;

    private UserCocktailCreateRequest userCocktailCreateRequest;

    private UserRecipeItemRequest userRecipeItemRequest;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        super.setUp(webApplicationContext, restDocumentationContextProvider);

        userRecipeItemRequest = UserRecipeItemRequest.builder()
                .ingredientId(1L)
                .quantity(123)
                .quantityUnit("SOJU").build();

        userCocktailCreateRequest = UserCocktailCreateRequest.builder()
                .name("name")
                .description("description")
                .userRecipeItemRequests(Collections.singletonList(userRecipeItemRequest))
                .build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("나만의 레시피를 생성한다.")
    @WithMockCustomUser
    @Test
    void addUserCocktail() throws Exception {
        given(userCocktailService.save(any(), any())).willReturn(1L);

        mockMvc.perform(post("/api/user-cocktails")
                .content(objectMapper.writeValueAsString(userCocktailCreateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/user-cocktails/1"))
                .andDo(print())
                .andDo(UserCocktailDocumentation.createUserCocktail());
    }
}