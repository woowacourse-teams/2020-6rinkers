package com.cocktailpick.back.common.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class PingDocumentation {
	public static RestDocumentationResultHandler ping() {
		return document("ping");
	}
}
