package com.cocktailpick.api.terminology.controller.acceptance;

import static com.cocktailpick.api.terminology.controller.acceptance.step.TerminologyAcceptanceStep.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.api.terminology.controller.Fixtures;
import com.cocktailpick.api.user.controller.acceptance.step.AuthAcceptanceStep;
import com.cocktailpick.core.terminology.dto.TerminologyRequest;
import com.cocktailpick.core.user.dto.AuthResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

@DisplayName("Terminology 인수/통합 테스트")
class TerminologyAcceptanceTest extends AcceptanceTest {

	@DisplayName("용어 csv 파일을 저장한다.")
	@Test
	void addTerminologiesByCsv() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		// when
		MultiPartSpecification csvFile = new MultiPartSpecBuilder(Fixtures.FOUR_TERMINOLOGIES_CSV_CONTENT.getBytes())
			.fileName("terminology.csv")
			.controlName("file")
			.mimeType(MediaType.TEXT_PLAIN_VALUE)
			.build();

		ExtractableResponse<Response> response = requestToAddTerminologiesByCsv(csvFile, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("용어를 추가한다.")
	@Test
	void createTerminology() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		// when
		TerminologyRequest terminologyRequest = new TerminologyRequest("보드카", "술", "러시아의 술",
			"http://www.cocktailpick.com");

		ExtractableResponse<Response> response = requestToAddTerminology(terminologyRequest, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("전체 용어 목록을 조회한다.")
	@Test
	void findTerminologies() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TerminologyRequest vodka = new TerminologyRequest("보드카", "술", "러시아의 술", "http://www.cocktailpick.com");
		TerminologyRequest shaker = new TerminologyRequest("쉐이커", "칵테일", "칵테일을 섞는 도구", "http://www.cocktailpick.com");
		TerminologyRequest layering = new TerminologyRequest("레이어링", "제조법", "층을 이뤄 칵테일을 만드는 기법",
			"http://www.cocktailpick.com");

		requestToAddTerminology(vodka, authResponse);
		requestToAddTerminology(shaker, authResponse);
		requestToAddTerminology(layering, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindTerminologies();

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		assertThatFindTerminologies(response, vodka, shaker, layering);
	}

	@DisplayName("단일 용어를 조회한다.")
	@Test
	void findTerminology() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TerminologyRequest vodka = new TerminologyRequest("보드카", "술", "러시아의 술", "http://www.cocktailpick.com");
		TerminologyRequest shaker = new TerminologyRequest("쉐이커", "칵테일", "칵테일을 섞는 도구", "http://www.cocktailpick.com");
		TerminologyRequest layering = new TerminologyRequest("레이어링", "제조법", "층을 이뤄 칵테일을 만드는 기법",
			"http://www.cocktailpick.com");

		String createLocation = requestToAddTerminologyAndGetLocation(vodka, authResponse);
		requestToAddTerminology(shaker, authResponse);
		requestToAddTerminology(layering, authResponse);

		// when
		ExtractableResponse<Response> response = requestToFindTerminology(createLocation);

		// then
		assertThatFindTerminology(createLocation, response, vodka);
		AcceptanceStep.assertThatStatusIsOk(response);
	}

	@DisplayName("용어를 수정한다.")
	@Test
	void updateTerminology() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TerminologyRequest vodka = new TerminologyRequest("보드카", "술", "러시아의 술", "http://www.cocktailpick.com");

		String createLocation = requestToAddTerminologyAndGetLocation(vodka, authResponse);

		// when
		TerminologyRequest updatingVodka = new TerminologyRequest("보드카", "술", "러시아의 술이고 도수는 40도 정도다.",
			"http://www.cocktailpick.com");

		ExtractableResponse<Response> response = requestToUpdateTerminology(createLocation, updatingVodka,
			authResponse);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
	}

	@DisplayName("용어를 삭제한다.")
	@Test
	void deleteTerminology() {
		// given
		AuthResponse authResponse = AuthAcceptanceStep.requestAdminAuth();

		TerminologyRequest vodka = new TerminologyRequest("보드카", "술", "러시아의 술", "http://www.cocktailpick.com");

		String createLocation = requestToAddTerminologyAndGetLocation(vodka, authResponse);

		// when
		ExtractableResponse<Response> response = requestToDeleteTerminology(createLocation, authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}
}
