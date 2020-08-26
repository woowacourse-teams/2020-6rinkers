package com.cocktailpick.back.common.acceptance.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PingAcceptanceStep {

    public static ExtractableResponse<Response> requestPing() {
        return given().log().all()
                .when()
                .get("/api/ping")
                .then().log().all()
                .extract();
    }

    public static void assertThatReceivePing(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
