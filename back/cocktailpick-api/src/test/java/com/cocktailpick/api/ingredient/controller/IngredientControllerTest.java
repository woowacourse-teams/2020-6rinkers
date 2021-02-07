package com.cocktailpick.api.ingredient.controller;

import com.cocktailpick.api.common.documentation.DocumentationWithSecurity;
import com.cocktailpick.api.ingredient.docs.IngredientDocumentation;
import com.cocktailpick.core.ingredient.dto.IngredientCreateRequest;
import com.cocktailpick.core.ingredient.dto.IngredientResponse;
import com.cocktailpick.core.ingredient.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = IngredientController.class)
public class IngredientControllerTest extends DocumentationWithSecurity {
    @MockBean
    private IngredientService ingredientService;

    private ObjectMapper objectMapper;

    private IngredientCreateRequest ingredientCreateRequest;

    private IngredientResponse ingredientResponse;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        super.setUp(webApplicationContext, restDocumentationContextProvider);
        ingredientCreateRequest = IngredientCreateRequest.builder()
                .title("test")
                .color("#000000")
                .abv(15.2)
                .build();

        ingredientResponse = IngredientResponse.builder()
                .title("test")
                .color("#000000")
                .abv(15.2)
                .build();

        objectMapper = new ObjectMapper();
    }

    @WithMockUser(roles = "USER")
    @DisplayName("재료를 생성한다.")
    @Test
    void createIngredient() throws Exception {
        given(ingredientService.save(any())).willReturn(1L);

        mockMvc.perform(post("/api/ingredients")
                .header("authorization", "Bearer ADMIN_TOKEN")
                .content(objectMapper.writeValueAsString(ingredientCreateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/ingredients/1"))
                .andDo(print())
                .andDo(IngredientDocumentation.createIngredient());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("재료를 모두 조회한다.")
    @Test
    void findAllIngredients() throws Exception {
        given(ingredientService.findAll()).willReturn(Collections.singletonList(ingredientResponse));

        mockMvc.perform(get("/api/ingredients")
                .header("authorization", "Bearer ADMIN_TOKEN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(IngredientDocumentation.findAll());
    }
}
