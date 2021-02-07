package com.cocktailpick.api.ingredient.docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

public class IngredientDocumentation {
    public static RestDocumentationResultHandler createIngredient() {
        return document("/ingredients/create",
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("재료 이름"),
                        fieldWithPath("color").type(JsonFieldType.STRING).description("재료 색깔"),
                        fieldWithPath("abv").type(JsonFieldType.NUMBER).description("재료 도수")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 재료 id")
                ));
    }
}
