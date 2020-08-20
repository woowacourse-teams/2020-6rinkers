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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cocktailpick.back.cocktail.docs.CocktailDocumentation;
import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.Flavor;
import com.cocktailpick.back.cocktail.dto.AbvAnswer;
import com.cocktailpick.back.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.dto.FlavorAnswer;
import com.cocktailpick.back.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.cocktail.dto.TagPreferenceAnswer;
import com.cocktailpick.back.cocktail.service.CocktailRecommendService;
import com.cocktailpick.back.cocktail.service.CocktailService;
import com.cocktailpick.back.cocktail.vo.UserPreferenceAnswer;
import com.cocktailpick.back.common.documentation.Documentation;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {CocktailController.class})
class CocktailControllerTest extends Documentation {
	@MockBean
	private CocktailService cocktailService;

	@MockBean
	private CocktailRecommendService cocktailRecommendService;

	private Cocktail blueHawaii;

	private CocktailRequest cocktailRequest;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);

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
				Collections.singletonList(new TagResponse(1L, "마지막 양심", "컨셉"))),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net",
				Arrays.asList(new TagResponse(1L, "쫄깃쫄깃", "식감"), new TagResponse(2L, "짭쪼름", "맛")))
		);
		given(cocktailService.findAllCocktails()).willReturn(cocktailResponses);

		mockMvc.perform(get("/api/cocktails")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.findCocktails());
	}

	@DisplayName("원하는 수만큼 페이징 된 칵테일을 조회한다.")
	@Test
	void findPagedCocktails() throws Exception {
		List<CocktailResponse> cocktailResponses = Arrays.asList(
			new CocktailResponse(1L, "싱가폴 슬링", "https://naver.com",
				Collections.singletonList(new TagResponse(1L, "마지막 양심", "컨셉"))),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net",
				Arrays.asList(new TagResponse(1L, "쫄깃쫄깃", "식감"), new TagResponse(2L, "짭쪼름", "맛")))
		);
		given(cocktailService.findPagedCocktails("", 0, 2)).willReturn(cocktailResponses);

		mockMvc.perform(get("/api/cocktails/pages")
			.param("id", "0")
			.param("size", "2")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.findPagedCocktails());
	}

	@DisplayName("칵테일을 단일 조회한다.")
	@Test
	void findCocktail() throws Exception {
		CocktailDetailResponse cocktailDetailResponse = CocktailDetailResponse.of(blueHawaii);
		cocktailDetailResponse = cocktailDetailResponse.withId(1L);
		given(cocktailService.findCocktail(anyLong())).willReturn(cocktailDetailResponse);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/cocktails/{id}", 1L)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.findCocktail());
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
			.andDo(print())
			.andDo(CocktailDocumentation.createCocktail());
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

		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/cocktails/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateCocktailRequest)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.updateCocktail());
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
			.andDo(print())
			.andDo(CocktailDocumentation.upload());
	}

	@DisplayName("칵테일을 삭제한다.")
	@Test
	void deleteCocktail() throws Exception {
		doNothing().when(cocktailService).deleteCocktail(any());

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/cocktails/{id}", 1L))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(CocktailDocumentation.deleteCocktail());
	}

	@DisplayName("모든 칵테일을 삭제한다.")
	@Test
	void deleteAllCocktails() throws Exception {
		doNothing().when(cocktailService).deleteAllCocktails();

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/cocktails"))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(CocktailDocumentation.deleteAllCocktails());
	}

	@DisplayName("오늘의 칵테일을 조회한다.")
	@Test
	void findCocktailOfToday() throws Exception {
		CocktailResponse cocktailResponse = CocktailResponse.of(blueHawaii).withId(1L);
		when(cocktailService.findCocktailOfToday()).thenReturn(cocktailResponse);

		mockMvc.perform(get("/api/cocktails/today"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(blueHawaii.getName()))
			.andExpect(jsonPath("$.imageUrl").value(blueHawaii.getImageUrl()))
			.andDo(print())
			.andDo(CocktailDocumentation.findTodayCocktail());
	}

	@DisplayName("칵테일을 추천한다.")
	@Test
	void recommendCocktail() throws Exception {
		CocktailDetailResponse blueHawaiiResponse = CocktailDetailResponse.of(blueHawaii);
		blueHawaiiResponse = blueHawaiiResponse.withId(1L);
		AbvAnswer abvAnswer = new AbvAnswer(100, 0);
		List<TagPreferenceAnswer> moodAnswers = Collections.singletonList(
			new TagPreferenceAnswer(10L, UserPreferenceAnswer.YES));
		List<TagPreferenceAnswer> preferenceAnswers = Arrays.asList(
			new TagPreferenceAnswer(5L, UserPreferenceAnswer.YES),
			new TagPreferenceAnswer(6L, UserPreferenceAnswer.YES)
		);
		List<TagPreferenceAnswer> nonPreferenceAnswers = Collections.singletonList(
			new TagPreferenceAnswer(11L, UserPreferenceAnswer.NO));
		FlavorAnswer flavorAnswer = new FlavorAnswer(UserPreferenceAnswer.SOSO, UserPreferenceAnswer.SOSO,
			UserPreferenceAnswer.SOSO);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswers, flavorAnswer,
			preferenceAnswers,
			nonPreferenceAnswers);

		given(cocktailRecommendService.recommend(any())).willReturn(Collections.singletonList(blueHawaiiResponse));

		mockMvc.perform(post("/api/cocktails/recommend")
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(recommendRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.recommendCocktail());
	}

	@DisplayName("특정 문자열을 포함하는 칵테일을 반환한다.")
	@Test
	void containName() throws Exception {
		CocktailResponse cocktailResponse = CocktailResponse.of(blueHawaii).withId(1L);
		given(cocktailService.findByNameContaining(anyString())).willReturn(
			Collections.singletonList(cocktailResponse));

		mockMvc.perform(get("/api/cocktails/auto-complete")
			.param("contain", "두강")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(CocktailDocumentation.contain());
	}
}