package com.cocktailpick.api.common.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.api.common.acceptance.step.AcceptanceStep;
import com.cocktailpick.api.common.acceptance.step.PingAcceptanceStep;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Ping 인수/통합 테스트")
class PingAcceptanceTest extends AcceptanceTest {

	@DisplayName("Ping 컨트롤러를 테스트한다.")
	@Test
	void ping() {
		// when
		ExtractableResponse<Response> response = PingAcceptanceStep.requestPing();

		// then
		AcceptanceStep.assertThatStatusIsOk(response);
	}
}
