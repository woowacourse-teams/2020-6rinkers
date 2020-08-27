package com.cocktailpick.back.common.acceptance.step;

import static io.restassured.RestAssured.*;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class PingAcceptanceStep {

    public static ExtractableResponse<Response> requestPing() {
        return given().log().all()
                .when()
                .get("/api/ping")
                .then().log().all()
                .extract();
    }
}
