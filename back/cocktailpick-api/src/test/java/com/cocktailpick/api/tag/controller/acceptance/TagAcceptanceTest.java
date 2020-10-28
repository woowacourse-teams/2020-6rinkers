package com.cocktailpick.api.tag.controller.acceptance;

import static com.cocktailpick.api.tag.controller.Fixtures.*;
import static com.cocktailpick.api.tag.controller.acceptance.step.TagAcceptanceStep.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.tag.dto.TagRequest;
import com.cocktailpick.core.user.dto.AuthResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

@DisplayName("Tag 인수/통합 테스트")
class TagAcceptanceTest extends AcceptanceTest {

	@DisplayName("태그 csv 파일을 저장한다.")
	@Test
	void addTagsByCsv() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		// when
		MultiPartSpecification csvFile = new MultiPartSpecBuilder(THREE_TAGS_CSV_CONTENT.getBytes())
			.fileName("tag.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		ExtractableResponse<Response> response = requestToAddTagsByCsv(csvFile, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("태그 목록을 조회한다.")
	@Test
	void findTags() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest sweet = new TagRequest("단맛", TagType.FLAVOR.getTagType());
		TagRequest sour = new TagRequest("신맛", TagType.FLAVOR.getTagType());
		TagRequest bitter = new TagRequest("쓴맛", TagType.FLAVOR.getTagType());

		requestToAddTag(sweet, authResponse);
		requestToAddTag(sour, authResponse);
		requestToAddTag(bitter, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindTags(TagType.FLAVOR, 2, false);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		assertThatFindTagsOfSweetAndSour(response);
	}

	@DisplayName("태그를 추가한다.")
	@Test
	void createTag() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		// when
		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		ExtractableResponse<Response> response = requestToAddTag(tagRequest, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("태그를 수정한다.")
	@Test
	void update() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		String createdLocation = requestToAddTagAndGetLocation(tagRequest, authResponse);

		// when
		TagRequest updateRequest = new TagRequest("단맛이지롱", TagType.FLAVOR.getTagType());

		ExtractableResponse<Response> response = requestToUpdateTag(createdLocation, updateRequest, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}

	@DisplayName("태그를 삭제한다.")
	@Test
	void delete() {
		// given
		AuthResponse authResponse = requestAdminAuth();

		TagRequest tagRequest = new TagRequest("단맛", TagType.FLAVOR.getTagType());

		String createdLocation = requestToAddTagAndGetLocation(tagRequest, authResponse);

		// when
		ExtractableResponse<Response> response = requestToDeleteTag(createdLocation, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}
}
