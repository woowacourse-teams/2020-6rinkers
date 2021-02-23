package com.cocktailpick.api.userCocktail.controller.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cocktailpick.api.common.WithMockCustomUser;
import com.cocktailpick.api.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.api.userCocktail.controller.UserCocktailController;
import com.cocktailpick.api.userCocktail.controller.docs.UserCocktailDocumentation;
import com.cocktailpick.core.usercocktail.dto.UserCocktailRequest;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponse;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponses;
import com.cocktailpick.core.usercocktail.dto.UserRecipeItemResponse;
import com.cocktailpick.core.usercocktail.service.UserCocktailService;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserCocktailController.class)
class UserCocktailControllerTest extends DocumentationWithSecurity {
    @MockBean
    private UserCocktailService userCocktailService;

    private UserCocktailRequest userCocktailRequest;

    private UserRecipeItemRequest userRecipeItemRequest;

    private UserCocktailResponse userCocktailResponse;

    private UserRecipeItemResponse userRecipeItemResponse;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentationContextProvider) {
        super.setUp(webApplicationContext, restDocumentationContextProvider);

        userRecipeItemRequest = UserRecipeItemRequest.builder()
            .ingredientId(1L)
            .quantity(123.0)
            .quantityUnit("SOJU").build();

        userCocktailRequest = UserCocktailRequest.builder()
            .name("name")
            .description("description")
            .userRecipeItemRequests(Collections.singletonList(userRecipeItemRequest))
            .build();

        userRecipeItemResponse = UserRecipeItemResponse.builder()
            .ingredientId(1L)
            .ingredientName("test ingredient")
            .ingredientAbv(1.0)
            .ingredientColor("#000000")
            .quantity(1.5)
            .quantityUnit("PAPER")
            .build();

        userCocktailResponse = new UserCocktailResponse(1L, "test", "test Description",
            Collections.singletonList(userRecipeItemResponse));

        objectMapper = new ObjectMapper();
    }

    @DisplayName("나만의 레시피를 생성한다.")
    @WithMockCustomUser
    @Test
    void addUserCocktail() throws Exception {
        given(userCocktailService.save(any(), any())).willReturn(1L);

        mockMvc.perform(post("/api/user-cocktails")
            .content(objectMapper.writeValueAsString(userCocktailRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/user-cocktails/1"))
            .andDo(print())
            .andDo(UserCocktailDocumentation.createUserCocktail());
    }

    @DisplayName("나만의 레시피를 단일 조회한다.")
    @WithMockCustomUser
    @Test
    void findUserCocktailById() throws Exception {
        given(userCocktailService.findUserCocktail(anyLong())).willReturn(userCocktailResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/user-cocktails/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(UserCocktailDocumentation.findUserCocktailById());
    }

    @DisplayName("나만의 레시피를 전체 조회한다.")
    @WithMockCustomUser
    @Test
    void findUserCocktails() throws Exception {
        UserCocktailResponses userCocktailResponses = new UserCocktailResponses(
            Collections.singletonList(userCocktailResponse));

        given(userCocktailService.findUserCocktails()).willReturn(userCocktailResponses);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/user-cocktails")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(UserCocktailDocumentation.findUserCocktails());
    }

    @DisplayName("나만의 레시피를 수정한다.")
    @WithMockCustomUser
    @Test
    void updateUserCocktail() throws Exception {
        UserCocktailRequest userCocktailupdateRequest = UserCocktailRequest.builder()
            .name("updateUserCocktail")
            .description("해피해킹 없이는 살 수 없는 몸이 돼었어")
            .userRecipeItemRequests(Collections.singletonList(userRecipeItemRequest))
            .build();

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/user-cocktails/{id}", 1L)
            .content(objectMapper.writeValueAsString(userCocktailupdateRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(UserCocktailDocumentation.updateUserCocktails());
    }

    @DisplayName("나만의 레시피를 삭제한다.")
    @WithMockCustomUser
    @Test
    void deleteUserCocktail() throws Exception {
        doNothing().when(userCocktailService).deleteUserCocktail(any(), anyLong());

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/user-cocktails/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(UserCocktailDocumentation.deleteUserCocktail());

    }
}