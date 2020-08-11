package com.cocktailpick.back.cocktail.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class CocktailDocumentation {
	public static RestDocumentationResultHandler createCocktail() {
		return document("cocktails/create",
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("칵테일 설명"),
				fieldWithPath("origin").type(JsonFieldType.STRING).description("칵테일 어원"),
				fieldWithPath("liquor").type(JsonFieldType.ARRAY).description("칵테일 액체 재료 이름"),
				fieldWithPath("liquorQuantity").type(JsonFieldType.ARRAY).description("칵테일 액체 재료 양"),
				fieldWithPath("special").type(JsonFieldType.ARRAY).description("칵테일 특수 재료 양"),
				fieldWithPath("specialQuantity").type(JsonFieldType.ARRAY).description("칵테일 특수 재료 양"),
				fieldWithPath("sweet").type(JsonFieldType.BOOLEAN).description("칵테일의 단 맛 유무"),
				fieldWithPath("sour").type(JsonFieldType.BOOLEAN).description("칵테일 신 맛 유무"),
				fieldWithPath("bitter").type(JsonFieldType.BOOLEAN).description("칵테일 쓴 맛 유무"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("칵테일 도수"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 주소"),
				fieldWithPath("tag").type(JsonFieldType.ARRAY).description("칵테일에 들어가는 태그 이름")
			),
			responseHeaders(
				headerWithName("Location").description("생성된 칵테일 id")
			));
	}

	public static RestDocumentationResultHandler findCocktails() {
		return document("cocktails/findAll",
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("칵테일 ID"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 URL"),
				fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("칵테일 태그 리스트"),
				fieldWithPath("[].tags.[].name").type(JsonFieldType.STRING).description("칵테일 태그 이름")
			));
	}

	public static RestDocumentationResultHandler findCocktail() {
		return document("cocktails/find",
			pathParameters(
				parameterWithName("id").description("조회할 칵테일 ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("조회한 칵테일 ID"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("칵테일 도수"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("칵테일 설명"),
				fieldWithPath("origin").type(JsonFieldType.STRING).description("칵테일 어원"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 주소"),
				fieldWithPath("tags").type(JsonFieldType.ARRAY).description("칵테일에 들어가는 태그 이름"),
				fieldWithPath("sweet").type(JsonFieldType.BOOLEAN).description("칵테일의 단 맛 유무"),
				fieldWithPath("sour").type(JsonFieldType.BOOLEAN).description("칵테일 신 맛 유무"),
				fieldWithPath("bitter").type(JsonFieldType.BOOLEAN).description("칵테일 쓴 맛 유무"),
				fieldWithPath("recipe").type(JsonFieldType.ARRAY).description("칵테일 재료 이름")
			)
		);
	}

	public static RestDocumentationResultHandler updateCocktail() {
		return document("cocktails/update",
			pathParameters(
				parameterWithName("id").description("수정할 칵테일 ID")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("칵테일 설명"),
				fieldWithPath("origin").type(JsonFieldType.STRING).description("칵테일 어원"),
				fieldWithPath("liquor").type(JsonFieldType.ARRAY).description("칵테일 액체 재료 이름"),
				fieldWithPath("liquorQuantity").type(JsonFieldType.ARRAY).description("칵테일 액체 재료 양"),
				fieldWithPath("special").type(JsonFieldType.ARRAY).description("칵테일 특수 재료 양"),
				fieldWithPath("specialQuantity").type(JsonFieldType.ARRAY).description("칵테일 특수 재료 양"),
				fieldWithPath("sweet").type(JsonFieldType.BOOLEAN).description("칵테일의 단 맛 유무"),
				fieldWithPath("sour").type(JsonFieldType.BOOLEAN).description("칵테일 신 맛 유무"),
				fieldWithPath("bitter").type(JsonFieldType.BOOLEAN).description("칵테일 쓴 맛 유무"),
				fieldWithPath("abv").type(JsonFieldType.NUMBER).description("칵테일 도수"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 주소"),
				fieldWithPath("tag").type(JsonFieldType.ARRAY).description("칵테일에 들어가는 태그 이름")
			)
		);
	}

	public static RestDocumentationResultHandler deleteCocktail() {
		return document("cocktails/delete",
			pathParameters(
				parameterWithName("id").description("수정할 칵테일 ID")
			));
	}

	public static RestDocumentationResultHandler findPagedCocktails() {
		return document("cocktails/findPagedCocktails",
			requestParameters(
				parameterWithName("id").description("조회할 페이지 번호"),
				parameterWithName("size").description("조회할 칵테일 갯수")
			),
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("칵테일 ID"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 URL"),
				fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("칵테일 태그 리스트"),
				fieldWithPath("[].tags.[].name").type(JsonFieldType.STRING).description("칵테일 태그 이름")
			));
	}

	public static RestDocumentationResultHandler upload() {
		return document("cocktails/upload",
			requestParts(
				partWithName("file").description("칵테일 csv 파일"))
		);
	}

	public static RestDocumentationResultHandler findTodayCocktail() {
		return document("cocktails/findTodayCocktail",
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("오늘의 칵테일 ID"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 URL"),
				fieldWithPath("tags").type(JsonFieldType.ARRAY).description("칵테일 태그 리스트")
			)
		);
	}

	public static RestDocumentationResultHandler recommendCocktail() {
		return document("cocktails/recommend",
			requestFields(
				fieldWithPath("[].answer").type(JsonFieldType.BOOLEAN).description("사용자 답변")
			),
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("추천된 칵테일 ID"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("[].abv").type(JsonFieldType.NUMBER).description("칵테일 도수"),
				fieldWithPath("[].description").type(JsonFieldType.STRING).description("칵테일 설명"),
				fieldWithPath("[].origin").type(JsonFieldType.STRING).description("칵테일 어원"),
				fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 주소"),
				fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("칵테일에 들어가는 태그 이름"),
				fieldWithPath("[].sweet").type(JsonFieldType.BOOLEAN).description("칵테일의 단 맛 유무"),
				fieldWithPath("[].sour").type(JsonFieldType.BOOLEAN).description("칵테일 신 맛 유무"),
				fieldWithPath("[].bitter").type(JsonFieldType.BOOLEAN).description("칵테일 쓴 맛 유무"),
				fieldWithPath("[].recipe").type(JsonFieldType.ARRAY).description("칵테일 재료 이름")
			)
		);
	}

	public static RestDocumentationResultHandler contain() {
		return document("cocktails/contain",
			requestParameters(
				parameterWithName("name").description("조회시, 포함되는 단어")
			),
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("칵테일 ID"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("칵테일 이름"),
				fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("칵테일 이미지 URL"),
				fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("칵테일 태그 리스트")
			));
	}
}
