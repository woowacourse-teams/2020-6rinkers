package com.cocktailpick.back.common.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.cocktailpick.back.common.acceptance.step.PingAcceptanceStep.assertThatReceivePing;
import static com.cocktailpick.back.common.acceptance.step.PingAcceptanceStep.requestPing;

@DisplayName("Ping 인수/통합 테스트")
public class PingAcceptanceTest extends AcceptanceTest {

    @DisplayName("Ping 컨트롤러를 테스트한다.")
    @Test
    void ping() {
        // when
        ExtractableResponse<Response> response = requestPing();

        // then
        assertThatReceivePing(response);
    }
}
