package com.cocktailpick.back.user.acceptance;

import static com.cocktailpick.back.cocktail.Fixtures.*;
import static com.cocktailpick.back.cocktail.acceptance.step.CocktailAcceptanceStep.*;
import static com.cocktailpick.back.common.acceptance.step.AcceptanceStep.*;
import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;
import static com.cocktailpick.back.user.acceptance.step.UserAcceptanceStep.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.acceptance.AcceptanceTest;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.user.dto.AuthResponse;
import com.cocktailpick.back.user.dto.LoginRequest;
import com.cocktailpick.back.user.dto.SignUpRequest;
import com.cocktailpick.back.user.dto.UserUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("User 인수/통합 테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    public static SignUpRequest signUpRequest;
    public static LoginRequest loginRequest;
    public static AuthResponse authResponse;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");
        requestSignUp(signUpRequest);

        loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");
        authResponse = requestTokenByLogin(loginRequest);
    }

    @DisplayName("현재 유저의 정보를 조회한다.")
    @Test
    void getCurrentUser() {

        // when
        ExtractableResponse<Response> response = requestToGetCurrentUser(authResponse);

        // then
        assertThatStatusIsOk(response);
        assertThatGetCurrentUserSuccess(response, signUpRequest);
    }

    @DisplayName("현재 유저의 정보를 수정한다.")
    @Test
    void updateUser() {
        //given
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");

        // when
        ExtractableResponse<Response> updateResponse = requestToUpdateUser(authResponse, userUpdateRequest);

        // then
        assertThatStatusIsOk(updateResponse);
    }

    @DisplayName("현재 유저가 탈퇴한다.")
    @Test
    void deleteUser() {

        // when
        ExtractableResponse<Response> response = requestToDeleteCurrentUser(authResponse);

        // then
        assertThatStatusIsNoContent(response);
    }

    @DisplayName("현재 유저가 탈퇴 후, 동일한 이메일로 회원가입한다.")
    @Test
    void deleteUserSignUp() {

        // when
        ExtractableResponse<Response> response = requestToDeleteCurrentUser(authResponse);

        // then
        assertThatStatusIsNoContent(response);

        // given
        signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");

        loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");

        requestSignUp(signUpRequest);
        AuthResponse reAuthResponse = requestTokenByLogin(loginRequest);

        // when
        ExtractableResponse<Response> getUserResponse = requestToGetCurrentUser(reAuthResponse);

        // then
        assertThatStatusIsOk(getUserResponse);
        assertThatGetCurrentUserSuccess(getUserResponse, signUpRequest);
    }

    @DisplayName("현재 유저에 즐겨찾기를 추가, 조회, 삭제한다.")
    @Test
    void findFavorites() {
        // given
        AuthResponse adminAuthResponse = requestAdminAuth();

        String kahluaLocation = requestToAddCocktailAndGetLocation(KAHLUA_MILK_REQUEST, adminAuthResponse);
        String malibuLocation = requestToAddCocktailAndGetLocation(MALIBU_ORANGE, adminAuthResponse);

        Long kahluaId = Long.parseLong(kahluaLocation.substring(15));
        Long malibuId = Long.parseLong(malibuLocation.substring(15));

        SignUpRequest signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");

        requestSignUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");

        AuthResponse authResponse = requestTokenByLogin(loginRequest);

        // when
        FavoriteRequest favoriteRequestOfKahlua = new FavoriteRequest(kahluaId);
        FavoriteRequest favoriteRequestOfMalibu = new FavoriteRequest(malibuId);

        requestToAddFavorite(authResponse, favoriteRequestOfKahlua);
        requestToAddFavorite(authResponse, favoriteRequestOfMalibu);

        // then
        ExtractableResponse<Response> responseAfterAdd = requestToFindFavorites(authResponse);

        assertThatAddAndFindFavoritesSuccess(responseAfterAdd, favoriteRequestOfKahlua, favoriteRequestOfMalibu);

        // when
        requestToDeleteFavorite(authResponse, kahluaId);

        // then
        ExtractableResponse<Response> responseAfterDelete = requestToFindFavorites(authResponse);

        assertThatDeleteAndFindFavoritesSuccess(responseAfterDelete, favoriteRequestOfMalibu);
    }
}
