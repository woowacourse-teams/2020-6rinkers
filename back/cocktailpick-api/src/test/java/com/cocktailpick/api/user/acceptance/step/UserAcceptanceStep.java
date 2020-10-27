package com.cocktailpick.api.user.acceptance.step;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;

import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.user.domain.AuthProvider;
import com.cocktailpick.core.user.domain.Role;
import com.cocktailpick.core.user.dto.AuthResponse;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.dto.UserUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class UserAcceptanceStep {
	public static ExtractableResponse<Response> requestToGetCurrentUser(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.when()
			.get("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToUpdateUser(AuthResponse authResponse,
		UserUpdateRequest userUpdateRequest) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(userUpdateRequest)
			.when()
			.patch("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToDeleteCurrentUser(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.when()
			.delete("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToAddFavorite(AuthResponse authResponse,
		FavoriteRequest favoriteRequest) {
		return given().log().all()
			.header(AUTHORIZATION, String.format("%s %s", authResponse.getTokenType(), authResponse.getAccessToken()))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(favoriteRequest)
			.when()
			.post("/api/user/me/favorites")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToFindFavorites(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, String.format("%s %s", authResponse.getTokenType(), authResponse.getAccessToken()))
			.when()
			.get("/api/user/me/favorites")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToDeleteFavorite(AuthResponse authResponse, Long cocktailId) {
		return given().log().all()
			.header(AUTHORIZATION, String.format("%s %s", authResponse.getTokenType(), authResponse.getAccessToken()))
			.when()
			.delete(String.format("/api/user/me/favorites?cocktailId=%d", cocktailId))
			.then().log().all()
			.extract();
	}

	public static void assertThatGetCurrentUserSuccess(ExtractableResponse<Response> response,
		SignUpRequest signUpRequest) {
		com.cocktailpick.common.user.dto.UserResponse userResponse = response.as(
			com.cocktailpick.common.user.dto.UserResponse.class);

		assertAll(
			() -> assertThat(userResponse.getEmail()).isEqualTo(signUpRequest.getEmail()),
			() -> assertThat(userResponse.getId()).isNotNull(),
			() -> assertThat(userResponse.getName()).isEqualTo(signUpRequest.getName()),
			() -> assertThat(userResponse.getProvider()).isEqualTo(AuthProvider.LOCAL.toString()),
			() -> assertThat(userResponse.getRole()).isEqualTo(Role.ROLE_USER.toString())
		);
	}

	public static void assertThatFindFavoritesSuccess(ExtractableResponse<Response> response,
		FavoriteRequest... favoriteRequests) {
		List<CocktailResponse> cocktailResponses = response.jsonPath().getList(".", CocktailResponse.class);
		List<Long> favoriteIds = Arrays.stream(favoriteRequests)
			.map(FavoriteRequest::getCocktailId)
			.collect(Collectors.toList());

		assertAll(
			() -> assertThat(cocktailResponses).hasSize(2),
			() -> assertThat(cocktailResponses).extracting(CocktailResponse::getId).containsExactlyInAnyOrderElementsOf(favoriteIds)
		);
	}
}
