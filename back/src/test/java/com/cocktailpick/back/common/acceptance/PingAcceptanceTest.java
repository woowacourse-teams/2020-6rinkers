package com.cocktailpick.back.common.acceptance;

import static com.cocktailpick.back.common.acceptance.step.AcceptanceStep.*;
import static com.cocktailpick.back.common.acceptance.step.PingAcceptanceStep.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Ping 인수/통합 테스트")
class PingAcceptanceTest extends AcceptanceTest {

	@DisplayName("Ping 컨트롤러를 테스트한다.")
	@Test
	void ping() {
		// when
		ExtractableResponse<Response> response = requestPing();

		// then
		assertThatStatusIsOk(response);
	}
}
