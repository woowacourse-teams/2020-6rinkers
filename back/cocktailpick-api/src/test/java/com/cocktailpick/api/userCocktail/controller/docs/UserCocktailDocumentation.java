package com.cocktailpick.api.userCocktail.controller.docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

public class UserCocktailDocumentation {
    public static RestDocumentationResultHandler createUserCocktail() {
        return document("UserCocktails/create",
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("칵테일 이름"),
                        fieldWithPath("description").type(JsonFieldType.STRING).description("칵테일 설명"),
                        fieldWithPath("userRecipeItemRequests").type(JsonFieldType.ARRAY).description("재료 목록"),
                        fieldWithPath("userRecipeItemRequests.[].ingredientId").type(JsonFieldType.NUMBER).description("재료 ID"),
                        fieldWithPath("userRecipeItemRequests.[].quantity").type(JsonFieldType.NUMBER).description("재료 양"),
                        fieldWithPath("userRecipeItemRequests.[].quantityUnit").type(JsonFieldType.STRING).description("재료 양 단위")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 칵테일 id")
                ));
    }
}
