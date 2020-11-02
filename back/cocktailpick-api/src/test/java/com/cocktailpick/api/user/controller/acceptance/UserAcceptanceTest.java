package com.cocktailpick.api.user.controller.acceptance;

import static com.cocktailpick.api.cocktail.acceptance.step.CocktailAcceptanceStep.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.api.cocktail.Fixtures;
import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.api.user.controller.acceptance.step.AuthAcceptanceStep;
import com.cocktailpick.api.user.controller.acceptance.step.UserAcceptanceStep;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.user.dto.AuthResponse;
import com.cocktailpick.core.user.dto.LoginRequest;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.dto.UserUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("User 인수/통합 테스트")
class UserAcceptanceTest extends AcceptanceTest {

	private static SignUpRequest signUpRequest;
	private static LoginRequest loginRequest;
	private static AuthResponse authResponse;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();

		// given
		signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");
		UserAcceptanceStep.requestSignUp(signUpRequest);

		loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");
		authResponse = AuthAcceptanceStep.requestTokenByLogin(loginRequest);
	}

	@DisplayName("현재 유저의 정보를 조회한다.")
	@Test
	void getCurrentUser() {

		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestToGetCurrentUser(authResponse);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		UserAcceptanceStep.assertThatGetCurrentUserSuccess(response, signUpRequest);
	}

	@DisplayName("현재 유저의 정보를 수정한다.")
	@Test
	void updateUser() {
		//given
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");

		// when
		ExtractableResponse<Response> updateResponse = UserAcceptanceStep.requestToUpdateUser(authResponse,
			userUpdateRequest);

		// then
		AcceptanceStep.assertThatStatusIsOk(updateResponse);
	}

	@DisplayName("현재 유저가 탈퇴한다.")
	@Test
	void deleteUser() {

		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestToDeleteCurrentUser(authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}

	@DisplayName("현재 유저가 탈퇴 후, 동일한 이메일로 회원가입한다.")
	@Test
	void deleteUserSignUp() {

		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestToDeleteCurrentUser(authResponse);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);

		// given
		signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");

		loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");

		UserAcceptanceStep.requestSignUp(signUpRequest);
		AuthResponse reAuthResponse = AuthAcceptanceStep.requestTokenByLogin(loginRequest);

		// when
		ExtractableResponse<Response> getUserResponse = UserAcceptanceStep.requestToGetCurrentUser(reAuthResponse);

		// then
		AcceptanceStep.assertThatStatusIsOk(getUserResponse);
		UserAcceptanceStep.assertThatGetCurrentUserSuccess(getUserResponse, signUpRequest);
	}

	@DisplayName("현재 유저에 즐겨찾기를 추가한다.")
	@Test
	void addFavorite() {
		// given
		AuthResponse adminAuthResponse = AuthAcceptanceStep.requestAdminAuth();

		String kahluaLocation = requestToAddCocktailAndGetLocation(Fixtures.KAHLUA_MILK_REQUEST, adminAuthResponse);

		Long kahluaId = Long.parseLong(kahluaLocation.substring(15));

		// when
		FavoriteRequest favoriteRequestOfKahlua = new FavoriteRequest(kahluaId);

		ExtractableResponse<Response> response = UserAcceptanceStep.requestToAddFavorite(authResponse,
			favoriteRequestOfKahlua);

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}

	@DisplayName("현재 유저에 즐겨찾기를 조회한다.")
	@Test
	void findFavorites() {
		// given
		AuthResponse adminAuthResponse = AuthAcceptanceStep.requestAdminAuth();

		String kahluaLocation = requestToAddCocktailAndGetLocation(Fixtures.KAHLUA_MILK_REQUEST, adminAuthResponse);
		String malibuLocation = requestToAddCocktailAndGetLocation(Fixtures.MALIBU_ORANGE, adminAuthResponse);

		Long kahluaId = Long.parseLong(kahluaLocation.substring(15));
		Long malibuId = Long.parseLong(malibuLocation.substring(15));

		FavoriteRequest favoriteRequestOfKahlua = new FavoriteRequest(kahluaId);
		FavoriteRequest favoriteRequestOfMalibu = new FavoriteRequest(malibuId);

		UserAcceptanceStep.requestToAddFavorite(authResponse, favoriteRequestOfKahlua);
		UserAcceptanceStep.requestToAddFavorite(authResponse, favoriteRequestOfMalibu);

		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestToFindFavorites(authResponse);

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
		UserAcceptanceStep.assertThatFindFavoritesSuccess(response, favoriteRequestOfKahlua, favoriteRequestOfMalibu);
	}

	@DisplayName("현재 유저에 즐겨찾기를 삭제한다.")
	@Test
	void deleteFavorite() {
		// given
		AuthResponse adminAuthResponse = AuthAcceptanceStep.requestAdminAuth();

		String kahluaLocation = requestToAddCocktailAndGetLocation(Fixtures.KAHLUA_MILK_REQUEST, adminAuthResponse);

		Long kahluaId = Long.parseLong(kahluaLocation.substring(15));

		FavoriteRequest favoriteRequestOfKahlua = new FavoriteRequest(kahluaId);

		UserAcceptanceStep.requestToAddFavorite(authResponse, favoriteRequestOfKahlua);

		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestToDeleteFavorite(authResponse, kahluaId);

		// then
		AcceptanceStep.assertThatStatusIsNoContent(response);
	}

	@DisplayName("회원가입 요청을 한다.")
	@Test
	void signUp() {
		// when
		ExtractableResponse<Response> response = UserAcceptanceStep.requestSignUp(
			new SignUpRequest("두강", "doo@gmail.com", "두강 비밀번호"));

		// then
		AcceptanceStep.assertThatStatusIsCreated(response);
	}
}
