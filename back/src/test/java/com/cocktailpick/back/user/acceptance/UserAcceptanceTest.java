package com.cocktailpick.back.user.acceptance;

import static com.cocktailpick.back.common.acceptance.step.AcceptanceStep.*;
import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;
import static com.cocktailpick.back.user.acceptance.step.UserAcceptanceStep.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.acceptance.AcceptanceTest;
import com.cocktailpick.back.user.dto.AuthResponse;
import com.cocktailpick.back.user.dto.LoginRequest;
import com.cocktailpick.back.user.dto.SignUpRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("User 인수/통합 테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    @DisplayName("현재 유저의 정보를 조회한다.")
    @Test
    void name() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");

        requestSignUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");

        AuthResponse authResponse = requestTokenByLogin(loginRequest);

        // when
        ExtractableResponse<Response> response = requestToGetCurrentUser(authResponse);

        // then
        assertThatStatusIsOk(response);
        assertThatGetCurrentUserSuccess(response, signUpRequest);
    }
}
