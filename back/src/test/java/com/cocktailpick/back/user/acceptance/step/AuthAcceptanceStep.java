package com.cocktailpick.back.user.acceptance.step;

import com.cocktailpick.back.user.dto.AuthResponse;
import com.cocktailpick.back.user.dto.LoginRequest;
import com.cocktailpick.back.user.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthAcceptanceStep {

    public static ExtractableResponse<Response> requestSignUp(SignUpRequest signUpRequest) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when()
                .post("/api/auth/signup")
                .then().log().all()
                .extract();
    }

    public static void assertThatSignUpSuccess(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> requestLogin(LoginRequest loginRequest) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract();
    }

    public static void assertThatLoginSuccess(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static AuthResponse requestTokenByLogin(LoginRequest loginRequest) {
        return requestLogin(loginRequest).as(AuthResponse.class);
    }
}
