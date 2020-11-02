package com.cocktailpick.api.user.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserDocumentation {
	public static RestDocumentationResultHandler findMe() {
		return document("user/find",
			requestHeaders(
				headerWithName("authorization").description("Bearer 토큰")),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("접속한 user의 id"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("접속한 user의 이름"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("접속한 user의 email"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("접속한 user의 imageUrl"),
				fieldWithPath("emailVerified").type(JsonFieldType.BOOLEAN).description("접속한 user의 email 인증 여부"),
				fieldWithPath("provider").type(JsonFieldType.STRING).description("접속한 user의 로그인 provider"),
				fieldWithPath("providerId").type(JsonFieldType.STRING).description("접속한 user의 provider id"),
				fieldWithPath("role").type(JsonFieldType.STRING).description("접속한 user의 권한"),
				fieldWithPath("myFavorites").type(JsonFieldType.ARRAY).description("접속한 user의 즐겨찾기")
			)
		);
	}

	public static RestDocumentationResultHandler updateUser() {
		return document("user/me",
			requestHeaders(
				headerWithName("authorization").description("Bearer 토큰")),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 user의 이름")
			)
		);
	}

	public static RestDocumentationResultHandler deleteMe() {
		return document("user/delete",
			requestHeaders(
				headerWithName("authorization").description("Bearer 토큰")));
	}

	public static RestDocumentationResultHandler findFavoriteCocktailIds() {
		return document("user/findFavoriteIds",
			requestHeaders(
				headerWithName("authorization").description("Bearer 토큰")
			),
			responseFields(
				fieldWithPath("ids").type(JsonFieldType.ARRAY).description("즐겨찾기 등록한 칵테일의 id들")
			));
	}
}
