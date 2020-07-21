package com.cocktailpick.back.cocktail.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.cocktailpick.back.cocktail.service.CocktailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {CocktailController.class})
class CocktailControllerTest {
	@MockBean
	private CocktailService cocktailService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}

	@DisplayName("칵테일을 전체 조회한다.")
	@Test
	void findCocktails() throws Exception {
		List<CocktailResponse> cocktailResponses = Arrays.asList(
			new CocktailResponse(1L, "싱가폴 슬링", "https://naver.com"),
			new CocktailResponse(2L, "블루 하와이", "https://daum.net")
		);
		given(cocktailService.findAllCocktails()).willReturn(cocktailResponses);

		mockMvc.perform(get("/api/cocktails")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("칵테일을 단일 조회한다.")
	@Test
	void findCocktail() throws Exception {
		Flavor flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();

		Cocktail blueHawaii = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();

		CocktailDetailResponse cocktailDetailResponse = CocktailDetailResponse.of(
			blueHawaii);

		given(cocktailService.findCocktail(anyLong())).willReturn(cocktailDetailResponse);

		mockMvc.perform(get("/api/cocktails/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("칵테일을 생성한다.")
	@Test
	void addCocktail() throws Exception {
		CocktailRequest cocktailRequest = CocktailRequest.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.bitter(true)
			.sour(true)
			.sweet(false)
			.liquor(Arrays.asList("두강이"))
			.liquorQuantity(Arrays.asList("두ml"))
			.tag(Arrays.asList("두강맛"))
			.special(new ArrayList<>())
			.specialQuantity(new ArrayList<>())
			.build();

		given(cocktailService.save(any())).willReturn(1L);

		mockMvc.perform(post("/api/cocktails")
			.content(new ObjectMapper().writeValueAsString(cocktailRequest))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
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
			.liquor(Arrays.asList("두강이"))
			.liquorQuantity(Arrays.asList("두ml"))
			.tag(Arrays.asList("두강맛"))
			.special(new ArrayList<>())
			.specialQuantity(new ArrayList<>())
			.build();

		mockMvc.perform(put("/api/cocktails/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(updateCocktailRequest)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("csv 파일로 칵테일을 저장한다.")
	@Test
	void addCocktailsByCsv() throws Exception {
		String content =
			"name,abv,description,origin,imageUrl,tag,sweet,sour,bitter,liquor,liquorQuantity,oz,special,specialQuantity,from\n"
				+ "갓마더,30,갓파더에서 위스키 대신 보드카를 사용한 칵테일입니다.,God Mother 대모라는 뜻입니다.,\"https://images.cocktailflow.com/v1/cocktail/w_300,h_540/cocktail_god_mother-1.png\",\"아몬드,부드러움\",1,,1,\"보드카,아마레또\",\"45,30\",1.5,\"설탕,메론\",\"1,2\",http://www.flickriver.com/photos/31027007@N08/31782815995/\n"
				+ "갓파더,39,\"베이스로 스카치 위스키를 사용합니다. 기주를 보드카로 바꾸면 '갓마더'로, 브랜디(코냑)로 바꾸면 '프렌치 커넥션'이 됩니다.\",영화 '대부'에서 비토 콜레오네(말론 브란도 역)가 마신 칵테일입니다.,https://s7d9.scene7.com/is/image/SAQ/godfather-ec?$saq-fiche-cocktail$,아몬드,1,,1,\"위스키,아마레또\",\"45,22.5\",1.5,,,http://www.flickriver.com/search/godfather+cocktail/\n"
				+ "그래스호퍼,15,\"크림을 사용하고, 녹색빛을 띄고 있는 민트향 칵테일입니다.\",칵테일의 색이 녹색이어서 메뚜기라는 이름이 붙었습니다.,https://banner2.kisspng.com/20180221/qde/kisspng-cocktail-sidecar-gimlet-martini-grasshopper-cocktail-5a8defe0899285.9571797915192514245635.jpg,\"민트,초코,부드러움\",1,1,,\"크렘 드 멘트,크렘 드 카카오, 우유\",\"30,30,30\",1,,,http://www.flickriver.com/search/grasshopper+cocktail/";

		doNothing().when(cocktailService).saveAll(any());

		mockMvc.perform(multipart("/api/cocktails/upload/csv")
			.file(
				new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes()))
			.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cocktails"))
			.andDo(print());

	}
}