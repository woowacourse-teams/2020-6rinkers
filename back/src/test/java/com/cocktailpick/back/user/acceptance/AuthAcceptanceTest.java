package com.cocktailpick.back.user.acceptance;

import static com.cocktailpick.back.common.acceptance.step.AcceptanceStep.*;
import static com.cocktailpick.back.user.acceptance.step.AuthAcceptanceStep.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.acceptance.AcceptanceTest;
import com.cocktailpick.back.user.dto.LoginRequest;
import com.cocktailpick.back.user.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Auth 인수/통합 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    public static SignUpRequest signUpRequest;
    public static LoginRequest loginRequest;

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
    public void signUp() {
        // when
        ExtractableResponse<Response> response = requestSignUp(signUpRequest);

        // then
        assertThatStatusIsCreated(response);
    }

    @DisplayName("로그인 요청을 한다.")
    @Test
    public void login() {
        // given
        requestSignUp(signUpRequest);

        // when
        ExtractableResponse<Response> response = requestLogin(loginRequest);

        // then
        assertThatStatusIsOk(response);
    }
}
