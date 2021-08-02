package com.cocktailpick.api.ingredient.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class IngredientDocumentation {
	public static RestDocumentationResultHandler createIngredient() {
		return document("ingredients/create",
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("재료 이름"),
				fieldWithPath("color").type(JsonFieldType.STRING).description("재료 색깔"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("재료 도수")
			),
			responseHeaders(
				headerWithName("Location").description("생성된 재료 ID")
			));
	}

	public static RestDocumentationResultHandler findAll() {
		return document("ingredients/findAll",
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("재료 id"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("재료 이름"),
				fieldWithPath("[].color").type(JsonFieldType.STRING).description("재료 색깔"),
				fieldWithPath("[].abv").type(JsonFieldType.NUMBER).description("재료 도수")
			));
	}

	public static RestDocumentationResultHandler findIngredient() {
		return document("ingredients/find",
			pathParameters(
				parameterWithName("id").description("조회할 재료 ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("재료 id"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("재료 이름"),
				fieldWithPath("color").type(JsonFieldType.STRING).description("재료 색깔"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("재료 도수")
			)
		);
	}

	public static RestDocumentationResultHandler updateIngredient() {
		return document("ingredients/update",
			pathParameters(
				parameterWithName("id").description("수정할 재료 ID")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("재료 이름"),
				fieldWithPath("color").type(JsonFieldType.STRING).description("재료 색깔"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("재료 도수")
			));
	}

	public static RestDocumentationResultHandler deleteIngredient() {
		return document("ingredients/delete",
			pathParameters(
				parameterWithName("id").description("삭제할 재료 ID")
			));
	}

	public static RestDocumentationResultHandler findIngredientByName() {
		return document("ingredients/findByName",
			requestParameters(parameterWithName("name").description("재료에 들어가야하는 이름")),
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("재료 id"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("재료 이름"),
				fieldWithPath("[].color").type(JsonFieldType.STRING).description("재료 색깔"),
				fieldWithPath("[].abv").type(JsonFieldType.NUMBER).description("재료 도수")
			));

	}
}
