package com.cocktailpick.core.cocktail.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.CocktailFindStrategyFactory;
import com.cocktailpick.core.cocktail.domain.CocktailRepository;
import com.cocktailpick.core.cocktail.domain.CocktailSearcher;
import com.cocktailpick.core.cocktail.domain.Flavor;
import com.cocktailpick.core.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.core.cocktail.dto.CocktailRequest;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.recipe.domain.RecipeItem;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagRepository;
import com.cocktailpick.core.tag.domain.TagType;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {
	private CocktailService cocktailService;

	@Mock
	private CocktailRepository cocktailRepository;

	@Mock
	private TagRepository tagRepository;

	@Mock
	private CocktailFindStrategyFactory cocktailFindStrategyFactory;

	@Mock
	private CocktailSearcher cocktailSearcher;

	private Tag tag;

	private Cocktail blueHawaii;

	private CocktailRequest cocktailRequest;

	private Flavor flavor;

	@BeforeEach
	void setUp() {
		cocktailService = new CocktailService(cocktailRepository, tagRepository,
			cocktailFindStrategyFactory);

		tag = Tag.builder().name("두강맛").tagType(TagType.FLAVOR).build();

		flavor = Flavor.builder()
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
	}

	@DisplayName("모든 칵테일을 조회한다.")
	@Test
	void findAllCocktails() {
		Cocktail peachCrush = Cocktail.builder()
			.name("피치 크러쉬")
			.build();

		Cocktail martini = Cocktail.builder()
			.name("마티니")
			.build();

		when(cocktailRepository.findAll()).thenReturn(Arrays.asList(peachCrush, martini));

		List<CocktailResponse> cocktails = cocktailService.findAllCocktails();

		assertThat(cocktails).extracting("name")
			.containsExactly(peachCrush.getName(), martini.getName());
	}

	@DisplayName("정해진 수만큼 칵테일을 조회한다.")
	@Test
	void findPagedCocktails() {
		Cocktail peachCrush = Cocktail.builder()
			.id(2L)
			.name("피치 크러쉬")
			.build();

		Cocktail martini = Cocktail.builder()
			.id(3L)
			.name("마티니")
			.build();

		Page<Cocktail> cocktailPage = new PageImpl<>(Arrays.asList(peachCrush, martini));

		when(cocktailRepository.findByNameContainingAndIdGreaterThan(
			any(), anyLong(), any())).thenReturn(cocktailPage);

		assertThat(cocktailService.findPageContainingWord("", 0, 2)).hasSize(2);
	}

	@DisplayName("특정 태그 목록이 포함된 칵테일을 원하는 수 만큼 조회한다.")
	@Test
	void findPageFilteredByTags() {
		Cocktail cocktail = Mockito.mock(Cocktail.class);
		when(cocktail.containTagIds(anyList())).thenReturn(true);

		List<Cocktail> cocktails = new ArrayList<>(Collections.nCopies(20, cocktail));
		when(cocktailRepository.findByIdGreaterThan(anyLong())).thenReturn(cocktails);

		assertThat(
			cocktailService.findPageFilteredByTags(Arrays.asList(0L, 1L, 2L), 0, 15)).hasSize(15);
	}

	@DisplayName("특정 태그 목록이 포함된 칵테일이 원하는 수보다 적을 경우 가능한 만큼 조회한다.")
	@Test
	void findPageFilteredByTags_WhenCocktailsSmallerThanSize() {
		Cocktail cocktail = Mockito.mock(Cocktail.class);
		when(cocktail.containTagIds(anyList())).thenReturn(true);

		List<Cocktail> cocktails = new ArrayList<>(Collections.nCopies(5, cocktail));
		when(cocktailRepository.findByIdGreaterThan(anyLong())).thenReturn(cocktails);

		assertThat(cocktailService.findPageFilteredByTags(Collections.emptyList(), 0, 15)).hasSize(
			5);
	}

	@DisplayName("단일 칵테일을 조회한다.")
	@Test
	void findCocktail() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(blueHawaii));

		CocktailDetailResponse cocktailDetailResponse = cocktailService.findCocktail(
			1L);

		org.junit.jupiter.api.Assertions.assertAll(
			() -> assertThat(cocktailDetailResponse.getAbv()).isEqualTo(
				blueHawaii.getAbv()),
			() -> assertThat(cocktailDetailResponse.getDescription()).isEqualTo(
				blueHawaii.getDescription()),
			() -> assertThat(cocktailDetailResponse.isSweet()).isEqualTo(
				blueHawaii.isSweet()),
			() -> assertThat(cocktailDetailResponse.isSour()).isEqualTo(
				blueHawaii.isSour()),
			() -> assertThat(cocktailDetailResponse.isBitter()).isEqualTo(
				blueHawaii.isBitter()),
			() -> assertThat(cocktailDetailResponse.getImageUrl()).isEqualTo(
				blueHawaii.getImageUrl()),
			() -> assertThat(cocktailDetailResponse.getName()).isEqualTo(
				blueHawaii.getName()),
			() -> assertThat(cocktailDetailResponse.getOrigin()).isEqualTo(
				blueHawaii.getOrigin()),
			() -> assertThat(cocktailDetailResponse.getTags()).isEmpty(),
			() -> assertThat(cocktailDetailResponse.getRecipe()).isEmpty()
		);
	}

	@DisplayName("단일 조회 시 해당하는 id가 없으면 예외 처리한다")
	@Test
	void findCocktailException() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.empty());

		Assertions.assertThatThrownBy(() -> cocktailService.findCocktail(0L))
			.isInstanceOf(EntityNotFoundException.class);
	}

	@DisplayName("칵테일을 생성한다.")
	@Test
	void save() {
		when(tagRepository.findByNameIn(anyList())).thenReturn(Collections.singletonList(tag));
		when(cocktailRepository.save(any())).thenReturn(blueHawaii);

		cocktailService.save(cocktailRequest);

		verify(cocktailRepository).save(any());
	}

	@DisplayName("칵테일을 수정한다.")
	@Test
	void update() {
		Tag bearTag = Tag.builder().name("곰").tagType(TagType.CONCEPT).build();

		RecipeItem recipeItem = RecipeItem.builder()
			.ingredient("두강이")
			.quantity("두ml")
			.build();

		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(blueHawaii));
		when(tagRepository.findByNameIn(anyList()))
			.thenReturn(Collections.singletonList(bearTag));

		cocktailService.updateCocktail(1L, cocktailRequest);

		org.junit.jupiter.api.Assertions.assertAll(
			() -> assertThat(blueHawaii.getName()).isEqualTo(cocktailRequest.getName()),
			() -> assertThat(blueHawaii.getDescription()).isEqualTo(cocktailRequest.getDescription()),
			() -> assertThat(blueHawaii.getTags()).isEqualTo(Collections.singletonList(bearTag)),
			() -> assertThat(blueHawaii.getRecipe().getRecipeItems().get(0).getIngredient()).isEqualTo(
				recipeItem.getIngredient())
		);
	}

	@DisplayName("칵테일을 삭제한다.")
	@Test
	void deleteCocktail() {
		cocktailService.deleteCocktail(1L);

		verify(cocktailRepository).deleteById(1L);
	}

	@DisplayName("모든 칵테일을 삭제한다.")
	@Test
	void deleteAllCocktails() {
		cocktailService.deleteAllCocktails();

		verify(cocktailRepository).deleteAll();
	}

	@DisplayName("여러 요청의 칵테일을 저장한다.")
	@Test
	void saveAll() {
		List<CocktailRequest> cocktailRequests = Arrays.asList(
			CocktailRequest.builder()
				.liquor(Collections.emptyList())
				.liquorQuantity(Collections.emptyList())
				.special(Collections.emptyList())
				.specialQuantity(Collections.emptyList())
				.tag(Collections.emptyList())
				.build(),
			CocktailRequest.builder()
				.liquor(Collections.emptyList())
				.liquorQuantity(Collections.emptyList())
				.special(Collections.emptyList())
				.specialQuantity(Collections.emptyList())
				.tag(Collections.emptyList())
				.build(),
			CocktailRequest.builder()
				.liquor(Collections.emptyList())
				.liquorQuantity(Collections.emptyList())
				.special(Collections.emptyList())
				.specialQuantity(Collections.emptyList())
				.tag(Collections.emptyList())
				.build()
		);

		cocktailService.saveAll(cocktailRequests);

		verify(tagRepository).findAll();
		verify(cocktailRepository).saveAll(any());
	}

	@DisplayName("오늘의 칵테일을 조회한다.")
	@Test
	void findCocktailOfToday() {
		Cocktail first = Cocktail.builder().name("두강 진").build();
		Cocktail second = Cocktail.builder().name("토니 진").build();
		Cocktail third = Cocktail.builder().name("작곰 진").build();

		when(cocktailFindStrategyFactory.createCocktailSearcher(anyLong()))
			.thenReturn(cocktailSearcher);
		when(cocktailSearcher.findIn(anyList())).thenReturn(second);
		when(cocktailRepository.findAll()).thenReturn(Arrays.asList(first, second, third));

		assertThat(cocktailService.findCocktailOfToday().getName()).isEqualTo("토니 진");
	}

	@Test
	void containName() {
		when(cocktailRepository.findByNameContaining("두강")).thenReturn(anyList());

		List<CocktailResponse> cocktailResponses = cocktailService.findByNameContaining("두강");
		assertThat(cocktailResponses).isNotNull();
	}
}