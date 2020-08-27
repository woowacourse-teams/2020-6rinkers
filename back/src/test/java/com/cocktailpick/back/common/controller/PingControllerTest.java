package com.cocktailpick.back.common.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.cocktailpick.back.common.docs.PingDocumentation;
import com.cocktailpick.back.common.documentation.DocumentationWithSecurity;

@WebMvcTest(controllers = PingController.class)
class PingControllerTest extends DocumentationWithSecurity {

	@DisplayName("핑 컨트롤러를 테스트한다.")
	@Test
	void ping() throws Exception {
        mockMvc.perform(get("/api/ping"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(PingDocumentation.ping());
    }
}