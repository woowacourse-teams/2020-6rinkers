package com.cocktailpick.api.user.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.api.common.acceptance.AcceptanceTest;
import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.api.user.acceptance.step.AuthAcceptanceStep;
import com.cocktailpick.core.user.dto.LoginRequest;
import com.cocktailpick.core.user.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Auth 인수/통합 테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    private static SignUpRequest signUpRequest;
    private static LoginRequest loginRequest;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        signUpRequest = new SignUpRequest("그니", "kuenhwi@gmail.com", "그니의 비밀번호");
        loginRequest = new LoginRequest("kuenhwi@gmail.com", "그니의 비밀번호");
    }

    @DisplayName("회원가입 요청을 한다.")
    @Test
    void signUp() {
        // when
		ExtractableResponse<Response> response = AuthAcceptanceStep.requestSignUp(signUpRequest);

        // then
		AcceptanceStep.assertThatStatusIsCreated(response);
    }

    @DisplayName("로그인 요청을 한다.")
    @Test
    void login() {
        // given
		AuthAcceptanceStep.requestSignUp(signUpRequest);

        // when
		ExtractableResponse<Response> response = AuthAcceptanceStep.requestLogin(loginRequest);

        // then
		AcceptanceStep.assertThatStatusIsOk(response);
    }
}
