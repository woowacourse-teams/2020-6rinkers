package com.cocktailpick.back.user.acceptance.step;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;

import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.dto.AuthResponse;
import com.cocktailpick.back.user.dto.SignUpRequest;
import com.cocktailpick.back.user.dto.UserResponse;

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

    public static void assertThatGetCurrentUserSuccess(ExtractableResponse<Response> response, SignUpRequest signUpRequest) {
        UserResponse userResponse = response.as(UserResponse.class);

        assertAll(
                () -> assertThat(userResponse.getEmail()).isEqualTo(signUpRequest.getEmail()),
                () -> assertThat(userResponse.getId()).isNotNull(),
                () -> assertThat(userResponse.getName()).isEqualTo(signUpRequest.getName()),
                () -> assertThat(userResponse.getProvider()).isEqualTo(AuthProvider.local.toString()),
                () -> assertThat(userResponse.getRole()).isEqualTo(Role.ROLE_USER.toString())
        );
    }
}
