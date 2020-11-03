package com.cocktailpick.api.cocktail.acceptance.step;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.http.MediaType;

import com.cocktailpick.api.user.controller.acceptance.step.AuthAcceptanceStep;
import com.cocktailpick.core.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.core.cocktail.dto.CocktailRequest;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.user.dto.AuthResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class CocktailAcceptanceStep {

	public static ExtractableResponse<Response> requestToAddCocktail(CocktailRequest cocktailRequest,
		AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(cocktailRequest)
			.when()
			.post("/api/cocktails")
			.then().log().all()
			.extract();
	}

	public static String requestToAddCocktailAndGetLocation(CocktailRequest cocktailRequest,
		AuthResponse authResponse) {
		return requestToAddCocktail(cocktailRequest, authResponse).header(LOCATION);
	}

	public static ExtractableResponse<Response> requestToFindCocktail(String url) {
		return given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get(url)
			.then().log().all()
			.extract();
	}

	public static void assertThatFindKahlua(ExtractableResponse<Response> response) {
		CocktailDetailResponse cocktailDetailResponse = response.as(CocktailDetailResponse.class);

		assertAll(
			() -> assertThat(cocktailDetailResponse.getId()).isNotNull(),
			() -> assertThat(cocktailDetailResponse.getName()).isEqualTo("깔루아 밀크"),
			() -> assertThat(cocktailDetailResponse.getAbv()).isEqualTo(20),
			() -> assertThat(cocktailDetailResponse.getDescription()).isEqualTo("커피 우유 맛"),
			() -> assertThat(cocktailDetailResponse.getOrigin()).isEqualTo("그니가 제일 좋아함"),
			() -> assertThat(cocktailDetailResponse.getImageUrl()).isEqualTo("https://www.naver.com"),
			() -> assertThat(cocktailDetailResponse.getTags().get(0).getTagType()).isEqualTo(
				TagType.FLAVOR.getTagType()),
			() -> assertThat(cocktailDetailResponse.getTags().get(0).getName()).isEqualTo("단맛"),
			() -> assertThat(cocktailDetailResponse.isSweet()).isTrue(),
			() -> assertThat(cocktailDetailResponse.isSour()).isFalse(),
			() -> assertThat(cocktailDetailResponse.isBitter()).isFalse(),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(0).getIngredient()).isEqualTo("깔루아"),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(0).getQuantity()).isEqualTo("15"),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(1).getIngredient()).isEqualTo("우유"),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(1).getQuantity()).isEqualTo("45"),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(2).getIngredient()).isEqualTo("설탕"),
			() -> assertThat(cocktailDetailResponse.getRecipe().get(2).getQuantity()).isEqualTo("1 스푼")
		);
	}

	public static ExtractableResponse<Response> requestToUpdateCocktail(String url, CocktailRequest cocktailRequest,
		AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(cocktailRequest)
			.when()
			.put(url)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToDeleteCocktail(String url, AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
			.when()
			.delete(url)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToAddCocktailsByCsv(
		MultiPartSpecification multiPartSpecification, AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart(multiPartSpecification)
			.when()
			.post("/api/cocktails/upload/csv")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToFindCocktailOfToday() {
		return given().log().all()
			.when()
			.get("/api/cocktails/today")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindCocktailOfToday(ExtractableResponse<Response> response) {
		assertThat(response.as(CocktailResponse.class)).isNotNull();
	}

	public static ExtractableResponse<Response> requestToDeleteAllCocktails(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, AuthAcceptanceStep.toHeaderValue(authResponse))
			.when()
			.delete("/api/cocktails")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToFindCocktails() {
		return given().log().all()
			.when()
			.get("/api/cocktails")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindThreeCocktails(ExtractableResponse<Response> response) {
		List<CocktailResponse> cocktailResponses = response.jsonPath().getList(".", CocktailResponse.class);

		assertThat(cocktailResponses).hasSize(3);
	}

	public static ExtractableResponse<Response> requestToRecommend(RecommendRequest recommendRequest) {
		return given().log().all()
			.body(recommendRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/api/cocktails/recommend")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToFindCocktailsByNameContaining(String word) {
		return given().log().all()
			.param("contain", word)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get("/api/cocktails/auto-complete")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindTwoCocktailsContainingGod(ExtractableResponse<Response> response) {
		List<CocktailResponse> cocktailsContainingGod = response.jsonPath().getList(".", CocktailResponse.class);

		assertAll(
			() -> assertThat(cocktailsContainingGod).hasSize(2),
			() -> assertThat(cocktailsContainingGod.get(0).getName()).contains("갓"),
			() -> assertThat(cocktailsContainingGod.get(1).getName()).contains("갓")
		);
	}

	public static ExtractableResponse<Response> requestToFindPageContainingWord(String contain, long id, int size) {
		return given().log().all()
			.param("contain", contain)
			.param("id", id)
			.param("size", size)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get("/api/cocktails/contain-word")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindOneCocktailContainingGod(ExtractableResponse<Response> response) {
		List<CocktailResponse> cocktailsContainingGod = response.jsonPath().getList(".", CocktailResponse.class);

		assertAll(
			() -> assertThat(cocktailsContainingGod).hasSize(1),
			() -> assertThat(cocktailsContainingGod.get(0).getName()).contains("갓")
		);
	}

	public static ExtractableResponse<Response> requestToFindPageFilteredByTags(List<Long> tagIds, long id, int size) {
		return given().log().all()
			.param("tagIds", tagIds)
			.param("id", id)
			.param("size", size)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get("/api/cocktails/contain-tags")
			.then().log().all()
			.extract();
	}

	public static void assertThatFindTwoCocktailContainingSoftness(ExtractableResponse<Response> response) {
		List<CocktailResponse> cocktailResponses = response.jsonPath().getList(".", CocktailResponse.class);

		assertAll(
			() -> assertThat(cocktailResponses).hasSize(2),
			() -> assertThat(cocktailResponses).extracting("name").contains("갓마더", "그래스호퍼")
		);
	}

	public static void assertThatFirstAttemptTakeLongerThanNextAttempt(ExtractableResponse<Response> firstAttempt,
		ExtractableResponse<Response> nextAttempt) {
		assertThat(firstAttempt.time()).isGreaterThan(nextAttempt.time());
	}

	public static void assertThatCocktailResponseSizeIsSameWith(Cache.ValueWrapper cachedCocktails, int size) {
		List<CocktailResponse> responses = (List<CocktailResponse>)cachedCocktails.get();

		assert responses != null;
		assertThat(responses.size()).isEqualTo(size);
	}

	public static void assertThatCachedCocktailsNull(Cache.ValueWrapper cachedCocktails) {
		assertThat(cachedCocktails).isNull();
	}
}
