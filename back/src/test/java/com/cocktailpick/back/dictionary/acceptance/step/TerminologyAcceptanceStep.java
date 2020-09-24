package com.cocktailpick.back.dictionary.acceptance.step;

import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;
import static io.restassured.RestAssured.*;
import static org.apache.http.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.springframework.http.MediaType;

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

	public static void assertThatFindTerminologies(ExtractableResponse<Response> response, TerminologyRequest vodka,
		TerminologyRequest shaker, TerminologyRequest layering) {
		List<TerminologyResponse> terminologyResponses = response.jsonPath().getList(".", TerminologyResponse.class);

		assertAll(
			() -> assertThat(terminologyResponses).hasSize(3),
			() -> assertThat(terminologyResponses.get(0).getName()).isEqualTo(vodka.getName()),
			() -> assertThat(terminologyResponses.get(0).getTerminologyType()).isEqualTo(
				vodka.getTerminologyType()),
			() -> assertThat(terminologyResponses.get(0).getDescription()).isEqualTo(vodka.getDescription()),
			() -> assertThat(terminologyResponses.get(0).getImageUrl()).isEqualTo(vodka.getImageUrl()),
			() -> assertThat(terminologyResponses.get(1).getName()).isEqualTo(shaker.getName()),
			() -> assertThat(terminologyResponses.get(1).getTerminologyType()).isEqualTo(
				shaker.getTerminologyType()),
			() -> assertThat(terminologyResponses.get(1).getDescription()).isEqualTo(shaker.getDescription()),
			() -> assertThat(terminologyResponses.get(1).getImageUrl()).isEqualTo(shaker.getImageUrl()),
			() -> assertThat(terminologyResponses.get(2).getName()).isEqualTo(layering.getName()),
			() -> assertThat(terminologyResponses.get(2).getTerminologyType()).isEqualTo(
				layering.getTerminologyType()),
			() -> assertThat(terminologyResponses.get(2).getDescription()).isEqualTo(layering.getDescription()),
			() -> assertThat(terminologyResponses.get(2).getImageUrl()).isEqualTo(layering.getImageUrl())
		);
	}

	public static ExtractableResponse<Response> requestToFindTerminology(String url) {
		return given().log().all()
			.when()
			.get(url)
			.then().log().all()
			.extract();
	}

	public static void assertThatFindTerminology(String url, ExtractableResponse<Response> response,
		TerminologyRequest vodka) {
		TerminologyResponse terminologyResponse = response.as(TerminologyResponse.class);
		String[] splitedUrl = url.split("/");
		Long id = Long.parseLong(splitedUrl[splitedUrl.length - 1]);

		assertAll(
			() -> assertThat(terminologyResponse.getId()).isEqualTo(id),
			() -> assertThat(terminologyResponse.getName()).isEqualTo(vodka.getName()),
			() -> assertThat(terminologyResponse.getTerminologyType()).isEqualTo(
				vodka.getTerminologyType()),
			() -> assertThat(terminologyResponse.getDescription()).isEqualTo(vodka.getDescription()),
			() -> assertThat(terminologyResponse.getImageUrl()).isEqualTo(vodka.getImageUrl())
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
