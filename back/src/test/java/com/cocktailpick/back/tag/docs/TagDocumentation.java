package com.cocktailpick.back.tag.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class TagDocumentation {
	public static RestDocumentationResultHandler upload() {
		return document("tags/upload",
			requestParts(
				partWithName("file").description("태그 csv 파일")
			)
		);
	}

	public static RestDocumentationResultHandler findTags() {
		return document("tags/findAll",
			responseFields(
				fieldWithPath("[].name").type(JsonFieldType.STRING).description("태그 이름"),
				fieldWithPath("[].tagType").type(JsonFieldType.STRING).description("태그 타입")
			)
		);
	}

	public static RestDocumentationResultHandler create() {
		return document("tags/create",
			requestFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("생성할 태그 이름"),
				fieldWithPath("tagType").type(JsonFieldType.STRING).description("생성할 태그의 타입")
			),
			responseHeaders(
				headerWithName("Location").description("생성한 태그 ID")
			)
		);
	}
}
