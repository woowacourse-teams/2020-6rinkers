package com.cocktailpick.api.terminology.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class TerminologyDocumentation {
	public static RestDocumentationResultHandler create() {
		return document("terminologies/create",
			requestFields(
				fieldWithPath("name").description("생성할 용어 이름"),
				fieldWithPath("terminologyType").description("생성할 용어 종류"),
				fieldWithPath("description").description("생성할 용어 설명"),
				fieldWithPath("imageUrl").description("생성할 용어 이미지 주소")
			),
			responseHeaders(
				headerWithName("Location").description("생성한 용어 ID")
			)
		);
	}

	public static RestDocumentationResultHandler upload() {
		return document("terminologies/upload",
			requestParts(
				partWithName("file").description("용어 csv 파일")
			)
		);
	}

	public static RestDocumentationResultHandler findAll() {
		return document("terminologies/findAll",
			responseFields(
				fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("조회된 용어 ID"),
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("조회된 용어 이름"),
				fieldWithPath("[].terminologyType").type(JsonFieldType.STRING).description("조회된 용어 종류"),
				fieldWithPath("[].description").type(JsonFieldType.STRING).description("조회된 용어 설명"),
				fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("조회된 용어 이미지 주소")
			));
	}

	public static RestDocumentationResultHandler find() {
		return document("terminologies/find",
			pathParameters(
				parameterWithName("id").description("조회할 용어의 ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("조회된 용어 ID"),
				fieldWithPath("name").type(JsonFieldType.STRING).description("조회된 용어 이름"),
				fieldWithPath("terminologyType").type(JsonFieldType.STRING).description("조회된 용어 종류"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("조회된 용어 설명"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("조회된 용어 이미지 주소")
			)
		);
	}

	public static RestDocumentationResultHandler update() {
		return document("terminologies/update",
			pathParameters(
				parameterWithName("id").description("수정할 용어의 ID")
			),
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("조회할 용어 이름"),
				fieldWithPath("terminologyType").type(JsonFieldType.STRING).description("조회할 용어 종류"),
				fieldWithPath("description").type(JsonFieldType.STRING).description("조회할 용어 설명"),
				fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("조회할 용어 이미지 주소")
			)
		);
	}

	public static RestDocumentationResultHandler delete() {
		return document("terminologies/delete",
			pathParameters(
				parameterWithName("id").description("삭제할 용어의 ID")
			)
		);
	}
}
