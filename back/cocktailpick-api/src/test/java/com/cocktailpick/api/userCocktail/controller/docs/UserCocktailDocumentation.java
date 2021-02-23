package com.cocktailpick.api.userCocktail.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserCocktailDocumentation {
	public static RestDocumentationResultHandler createUserCocktail() {
		return document("UserCocktails/create",
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("나만의 레시피 이름"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("나만의 레시피 설명"),
				fieldWithPath("userRecipeItemRequests").type(JsonFieldType.ARRAY).description("재료 목록"),
				fieldWithPath("userRecipeItemRequests.[].ingredientId").type(JsonFieldType.NUMBER).description("재료 ID"),
				fieldWithPath("userRecipeItemRequests.[].quantity").type(JsonFieldType.NUMBER).description("재료 양"),
				fieldWithPath("userRecipeItemRequests.[].quantityUnit").type(JsonFieldType.STRING)
					.description("재료 양 단위")
			),
			responseHeaders(
				headerWithName("Location").description("생성된 칵테일 id")
			));
	}

	public static RestDocumentationResultHandler findUserCocktailById() {
		return document("UserCocktails/find",
			pathParameters(
				parameterWithName("id").description("수정할 칵테일 ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("나만의 레시피 아이디"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("나만의 레시피 이름"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("나만의 레시피 설명"),
				fieldWithPath("userRecipeItemResponses").type(JsonFieldType.ARRAY).description("재료 목록"),
				fieldWithPath("userRecipeItemResponses.[].ingredientId").type(JsonFieldType.NUMBER)
					.description("재료 ID"),
				fieldWithPath("userRecipeItemResponses.[].ingredientName").type(JsonFieldType.STRING)
					.description("재료 이름"),
				fieldWithPath("userRecipeItemResponses.[].ingredientColor").type(JsonFieldType.STRING)
					.description("재료 색"),
				fieldWithPath("userRecipeItemResponses.[].ingredientAbv").type(JsonFieldType.NUMBER)
					.description("재료 도수"),
				fieldWithPath("userRecipeItemResponses.[].quantity").type(JsonFieldType.NUMBER).description("재료 양"),
				fieldWithPath("userRecipeItemResponses.[].quantityUnit").type(JsonFieldType.STRING)
					.description("재료 양 단위")
			));
	}

	public static RestDocumentationResultHandler findUserCocktails() {
		return document("UserCocktails/findAll",
			responseFields(
				fieldWithPath("userCocktailResponses").type(JsonFieldType.ARRAY).description("나만의 레시피 목록"),
				fieldWithPath("userCocktailResponses.[].id").type(JsonFieldType.NUMBER).description("나만의 레시피 아이디"),
				fieldWithPath("userCocktailResponses.[].name").type(JsonFieldType.STRING).description("나만의 레시피 이름"),
				fieldWithPath("userCocktailResponses.[].description").type(JsonFieldType.STRING)
					.description("나만의 레시피 설명"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses").type(JsonFieldType.ARRAY)
					.description("재료 목록"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].ingredientId").type(
					JsonFieldType.NUMBER)
					.description("재료 ID"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].ingredientName").type(
					JsonFieldType.STRING)
					.description("재료 이름"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].ingredientColor").type(
					JsonFieldType.STRING)
					.description("재료 색"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].ingredientAbv").type(
					JsonFieldType.NUMBER)
					.description("재료 도수"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].quantity").type(JsonFieldType.NUMBER)
					.description("재료 양"),
				fieldWithPath("userCocktailResponses.[].userRecipeItemResponses.[].quantityUnit").type(
					JsonFieldType.STRING)
					.description("재료 양 단위")
			));
	}

	public static RestDocumentationResultHandler updateUserCocktails() {
		return document("UserCocktails/update",
			pathParameters(
				parameterWithName("id").description("수정할 칵테일 ID")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("업데이트될 나만의 레시피 이름"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("업데이트 될 나만의 레시피 설명"),
				fieldWithPath("userRecipeItemRequests").type(JsonFieldType.ARRAY).description("업데이트 될 재료 목록"),
				fieldWithPath("userRecipeItemRequests.[].ingredientId").type(JsonFieldType.NUMBER)
					.description("업데이트 될 재료 ID"),
				fieldWithPath("userRecipeItemRequests.[].quantity").type(JsonFieldType.NUMBER)
					.description("업데이트 될 재료 양"),
				fieldWithPath("userRecipeItemRequests.[].quantityUnit").type(JsonFieldType.STRING)
					.description("업데이트 될 재료 양 단위")
			)
		);
	}

	public static RestDocumentationResultHandler deleteUserCocktail() {
		return document("UserCocktails/delete",
			pathParameters(
				parameterWithName("id").description("수정할 칵테일 ID")
			));
	}
}
