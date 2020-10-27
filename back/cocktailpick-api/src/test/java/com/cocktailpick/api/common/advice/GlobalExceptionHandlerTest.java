package com.cocktailpick.api.common.advice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.cocktailpick.core.common.exceptions.BusinessException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = {GlobalExceptionHandlerTest.TestController.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = GlobalExceptionHandlerTest.TestController.class)
class GlobalExceptionHandlerTest {
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();

		objectMapper = new ObjectMapper();
	}

	@DisplayName("잘못된 input이 들어오면 예외 처리한다.")
	@Test
	void handleMethodArgumentNotValidException() throws Exception {
		TestDto testDto = new TestDto(0);

		mockMvc.perform(post("/exception")
			.content(objectMapper.writeValueAsString(testDto))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("C_003"))
			.andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("intVar")))
			.andDo(print());
	}

	@DisplayName("잘못된 인자가 들어올 경우 예외 처리한다.")
	@Test
	void handleMethodArgumentTypeMismatchException() throws Exception {
		String input = "hi";
		mockMvc.perform(get("/exception/" + input)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("C_004"))
			.andDo(print());
	}

	@DisplayName("잘못된 Http 메서드로 요청이 왔을 때 예외 처리한다.")
	@Test
	void handleHttpRequestMethodNotSupportedException() throws Exception {
		mockMvc.perform(delete("/exception"))
			.andExpect(status().isMethodNotAllowed())
			.andExpect(jsonPath("$.code").value("C_002"))
			.andDo(print());
	}

	@DisplayName("비즈니스 예외 처리한다.")
	@Test
	void handleBusinessException() throws Exception {
		mockMvc.perform(get("/exception/1"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("C_005"))
			.andDo(print());
	}

	@DisplayName("비즈니스 이외의 예외를 처리한다.")
	@Test
	void handleException() throws Exception {
		mockMvc.perform(post("/exception")
			.content(objectMapper.writeValueAsString(new TestDto(1))))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value("C_001"))
			.andDo(print());
	}

	private static class TestDto {
		@Positive
		private int intVar;

		public TestDto() {
		}

		public TestDto(int intVar) {
			this.intVar = intVar;
		}

		public int getIntVar() {
			return intVar;
		}
	}

	@RestController
	@RequestMapping("/exception")
	static class TestController {
		@GetMapping("/{id}")
		public String executeBusinessException(@PathVariable Long id) {
			throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
		}

		@PostMapping
		public String executeException(@RequestBody @Valid TestDto testDto) {
			throw new RuntimeException();
		}
	}
}