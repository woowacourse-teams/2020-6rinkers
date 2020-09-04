package com.cocktailpick.back.dictionary.acceptance.step;

import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;
import static io.restassured.RestAssured.*;
import static org.apache.http.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.springframework.http.MediaType;

import com.cocktailpick.back.dictionary.domain.TerminologyType;
import com.cocktailpick.back.dictionary.dto.TerminologyRequest;
import com.cocktailpick.back.dictionary.dto.TerminologyResponse;
import com.cocktailpick.back.user.dto.AuthResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class TerminologyAcceptanceStep {

	public static ExtractableResponse<Response> requestToAddTerminologiesByCsv(
		MultiPartSpecification multiPartSpecification, AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart(multiPartSpecification)
			.when()
			.post("/api/terminologies/upload/csv")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToAddTerminology(TerminologyRequest terminologyRequest,
		AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(terminologyRequest)
			.when()
			.post("/api/terminologies")
			.then().log().all()
			.extract();
	}

	public static String requestToAddTerminologyAndGetLocation(TerminologyRequest terminologyRequest,
		AuthResponse authResponse) {
		return requestToAddTerminology(terminologyRequest, authResponse).header(LOCATION);
	}

	public static ExtractableResponse<Response> requestToFindTerminologies() {
		return given().log().all()
			.when()
			.get("/api/terminologies")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindTerminologiesOfShaker(ExtractableResponse<Response> response) {
		List<TerminologyResponse> terminologyResponses = response.jsonPath().getList(".", TerminologyResponse.class);

		assertAll(
			() -> assertThat(terminologyResponses).hasSize(3),
			() -> assertThat(terminologyResponses.get(1).getName()).isEqualTo("쉐이커"),
			() -> assertThat(terminologyResponses.get(1).getTerminologyType()).isEqualTo(
				TerminologyType.COCKTAIL.getKoreanName()),
			() -> assertThat(terminologyResponses.get(1).getDescription()).contains("섞는 도구"),
			() -> assertThat(terminologyResponses.get(1).getImageUrl()).contains("cocktailpick.com")
		);
	}

	public static ExtractableResponse<Response> requestToFindTerminology(String url) {
		return given().log().all()
			.when()
			.get(url)
			.then().log().all()
			.extract();
	}

	public static void assertThatFindTerminology(String url, ExtractableResponse<Response> response) {
		TerminologyResponse terminologyResponse = response.as(TerminologyResponse.class);
		String[] splitedUrl = url.split("/");
		Long id = Long.parseLong(splitedUrl[splitedUrl.length - 1]);

		assertAll(
			() -> assertThat(terminologyResponse.getId()).isEqualTo(id),
			() -> assertThat(terminologyResponse.getName()).isEqualTo("보드카"),
			() -> assertThat(terminologyResponse.getTerminologyType()).isEqualTo(
				TerminologyType.ALCOHOL.getKoreanName()),
			() -> assertThat(terminologyResponse.getDescription()).contains("러시아"),
			() -> assertThat(terminologyResponse.getImageUrl()).contains("cocktailpick.com")
		);
	}

	public static ExtractableResponse<Response> requestToUpdateTerminology(String url,
		TerminologyRequest terminologyRequest,
		AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(terminologyRequest)
			.when()
			.put(url)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToDeleteTerminology(String url, AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.when()
			.delete(url)
			.then().log().all()
			.extract();
	}
}
