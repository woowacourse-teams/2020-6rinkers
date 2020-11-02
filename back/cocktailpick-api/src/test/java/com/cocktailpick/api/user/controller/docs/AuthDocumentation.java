package com.cocktailpick.api.user.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class AuthDocumentation {
	public static RestDocumentationResultHandler login() {
		return document("auth/login",
			requestFields(
				fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 email"),
				fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 password")
			),
			responseFields(
				fieldWithPath("tokenType").type(JsonFieldType.STRING).description("Token의 타입"),
				fieldWithPath("accessToken").type(JsonFieldType.STRING).description("로그인으로 얻은 Token")
			)
		);
	}

	public static RestDocumentationResultHandler signup() {
		return document("auth/signup",
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("회원가입할 이름"),
				fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입할 E-mail"),
				fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입할 스워드")
			),
			responseHeaders(
				headerWithName("Location").description("회원가입한 유저 Id")
			)
		);
	}
}
