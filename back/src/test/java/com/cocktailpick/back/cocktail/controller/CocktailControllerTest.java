package com.cocktailpick.back.cocktail.controller;

import static com.cocktailpick.back.cocktail.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.Flavor;
import com.cocktailpick.back.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.dto.UserRecommendRequest;
import com.cocktailpick.back.cocktail.service.CocktailService;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {CocktailController.class})
class CocktailControllerTest {
	@MockBean
	private CocktailService cocktailService;

	private MockMvc mockMvc;

	private Cocktail blueHawaii;

	private CocktailRequest cocktailRequest;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();

		Flavor flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();

		blueHawaii = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();

		cocktailRequest = CocktailRequest.builder()
			.abv(40)
			.description("곰 맛 칵테일")
			.imageUrl("https://naver.com")
			.name("iceBear")
			.origin("두원이는 강하다.")
			.bitter(true)
			.sour(true)
			.sweet(false)
			.liquor(Collections.singletonList("두강이"))
			.liquorQuantity(Collections.singletonList("두ml"))
			.tag(Collections.singletonList("곰"))
			.special(new ArrayList<>())
			.specialQuantity(new ArrayList<>())
			.build();

		objectMapper = new ObjectMapper();
	}

	@DisplayName("칵테일을 전체 조회한다.")
	@Test
	void findCocktails() throws Exception {
		List<CocktailResponse> cocktailResponses = Arrays.asList(
			new CocktailResponse(1L, "싱가폴 슬링", "https://naver.com",
				Collections.singletonList(new TagResponse("마지막 양심"))),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net",
				Arrays.asList(new TagResponse("쫄깃쫄깃"), new TagResponse("짭쪼름")))
		);
		given(cocktailService.findAllCocktails()).willReturn(cocktailResponses);

		mockMvc.perform(get("/api/cocktails")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("원하는 수만큼 페이징 된 칵테일을 조회한다.")
	@Test
	void findPagedCocktails() throws Exception {
		List<CocktailResponse> cocktailResponses = Arrays.asList(
			new CocktailResponse(1L, "싱가폴 슬링", "https://naver.com",
				Collections.singletonList(new TagResponse("마지막 양심"))),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net",
				Arrays.asList(new TagResponse("쫄깃쫄깃"), new TagResponse("짭쪼름")))
		);
		given(cocktailService.findPagedCocktails("", 0, 2)).willReturn(cocktailResponses);

		mockMvc.perform(get("/api/cocktails/pages?id=0&size=2")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("칵테일을 단일 조회한다.")
	@Test
	void findCocktail() throws Exception {
		CocktailDetailResponse cocktailDetailResponse = CocktailDetailResponse.of(blueHawaii);

		given(cocktailService.findCocktail(anyLong())).willReturn(cocktailDetailResponse);

		mockMvc.perform(get("/api/cocktails/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("칵테일을 생성한다.")
	@Test
	void addCocktail() throws Exception {
		given(cocktailService.save(any())).willReturn(1L);

		mockMvc.perform(post("/api/cocktails")
			.content(objectMapper.writeValueAsString(cocktailRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cocktails/1"))
			.andDo(print());
	}

	@DisplayName("칵테일을 수정한다.")
	@Test
	void updateCocktail() throws Exception {
		doNothing().when(cocktailService).updateCocktail(anyLong(), any());

		CocktailRequest updateCocktailRequest = CocktailRequest.builder()
			.abv(40)
			.description("작곰 맛 칵테일")
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.bitter(true)
			.sour(true)
			.sweet(false)
			.liquor(Collections.singletonList("두강이"))
			.liquorQuantity(Collections.singletonList("두ml"))
			.tag(Collections.singletonList("두강맛"))
			.special(new ArrayList<>())
			.specialQuantity(new ArrayList<>())
			.build();

		mockMvc.perform(put("/api/cocktails/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateCocktailRequest)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("csv 파일로 칵테일을 저장한다.")
	@Test
	void addCocktailsByCsv() throws Exception {
		doNothing().when(cocktailService).saveAll(any());

		mockMvc.perform(multipart("/api/cocktails/upload/csv")
			.file(
				new MockMultipartFile("file", "test.csv", "text/csv", THREE_COCKTAILS_CSV_CONTENT.getBytes()))
			.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cocktails"))
			.andDo(print());

	}

	@DisplayName("칵테일을 삭제한다.")
	@Test
	void deleteCocktail() throws Exception {
		doNothing().when(cocktailService).deleteCocktail(any());

		mockMvc.perform(delete("/api/cocktails/1"))
			.andExpect(status().isNoContent())
			.andDo(print());
	}

	@DisplayName("오늘의 칵테일을 조회한다.")
	@Test
	void findCocktailOfToday() throws Exception {
		when(cocktailService.findCocktailOfToday()).thenReturn(
			CocktailResponse.of(blueHawaii));

		mockMvc.perform(get("/api/cocktails/today"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(blueHawaii.getName()))
			.andExpect(jsonPath("$.imageUrl").value(blueHawaii.getImageUrl()))
			.andDo(print());
	}

	@DisplayName("칵테일을 추천한다.")
	@Test
	void recommendCocktail() throws Exception {
		CocktailDetailResponse blueHawaiiResponse = CocktailDetailResponse.of(blueHawaii);
		List<UserRecommendRequest> requests = Arrays.asList(new UserRecommendRequest(true), new UserRecommendRequest(true));

		given(cocktailService.recommend(any())).willReturn(Collections.singletonList(blueHawaiiResponse));

		mockMvc.perform(post("/api/cocktails/recommend")
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(requests))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("특정 문자열을 포함하는 칵테일을 반환한다.")
	@Test
	void containName() throws Exception {
		given(cocktailService.findByNameContaining(anyString())).willReturn(anyList());

		mockMvc.perform(get("/api/cocktails/auto-complete")
			.param("contain", "두강")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}