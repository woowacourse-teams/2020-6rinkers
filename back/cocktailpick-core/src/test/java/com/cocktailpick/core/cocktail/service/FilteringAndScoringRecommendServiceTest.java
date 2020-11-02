package com.cocktailpick.core.cocktail.service;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.Flavor;
import com.cocktailpick.core.cocktail.dto.AbvAnswer;
import com.cocktailpick.core.cocktail.dto.FlavorAnswer;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.cocktail.dto.TagPreferenceAnswer;
import com.cocktailpick.core.cocktail.vo.UserPreferenceAnswer;
import com.cocktailpick.core.tag.domain.CocktailTag;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.util.EntityMapper;

class FilteringAndScoringRecommendServiceTest {
	private Flavor flavor;

	private Cocktail cocktail1;

	private Cocktail cocktail2;

	private Cocktail cocktail3;

	private Cocktail cocktail4;

	private FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	private EntityMapper<Long, Tag> entityMapper;

	private List<Cocktail> cocktails;
	private AbvAnswer abvAnswer;
	private List<TagPreferenceAnswer> moodAnswer;
	private List<TagPreferenceAnswer> preferenceAnswers;
	private List<TagPreferenceAnswer> nonPreferenceAnswers;
	private FlavorAnswer flavorAnswer;

	@BeforeEach
	void setUp() {
		filteringAndScoringRecommendService = new FilteringAndScoringRecommendService();

		flavor = Flavor.builder()
			.bitter(false)
			.sweet(false)
			.sour(false)
			.build();

		cocktail1 = Cocktail.builder()
			.name("a")
			.abv(10)
			.flavor(flavor)
			.build();
		cocktail2 = Cocktail.builder()
			.name("b")
			.abv(20)
			.flavor(flavor)
			.build();
		cocktail3 = Cocktail.builder()
			.name("c")
			.abv(30)
			.flavor(flavor)
			.build();
		cocktail4 = Cocktail.builder()
			.name("d")
			.abv(40)
			.flavor(flavor)
			.build();

		List<Tag> tags = Arrays.asList(
			Tag.builder().name("도수가 높은").tagType(TagType.ABV).build(),
			Tag.builder().name("단맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().name("신맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().name("쓴맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().name("탄산").tagType(TagType.INGREDIENT).build(),
			Tag.builder().name("멜론").tagType(TagType.INGREDIENT).build(),
			Tag.builder().name("커피").tagType(TagType.DISLIKE).build(),
			Tag.builder().name("초코").tagType(TagType.DISLIKE).build(),
			Tag.builder().name("코코넛").tagType(TagType.INGREDIENT).build(),
			Tag.builder().name("우유").tagType(TagType.DISLIKE).build(),
			Tag.builder().name("연인과 함께").tagType(TagType.CONCEPT).build(),
			Tag.builder().name("레몬").tagType(TagType.INGREDIENT).build());

		Map<Long, Tag> map = IntStream.rangeClosed(1, tags.size())
			.boxed()
			.collect(toMap(Long::valueOf, i -> tags.get(i - 1)));
		entityMapper = new EntityMapper<>(map);

		CocktailTag.associate(cocktail1, tags.get(0));  //도수가 높은
		CocktailTag.associate(cocktail1, tags.get(1));  //단맛
		CocktailTag.associate(cocktail1, tags.get(7));  //초코
		CocktailTag.associate(cocktail1, tags.get(10));  //레몬

		CocktailTag.associate(cocktail2, tags.get(2));  //신맛
		CocktailTag.associate(cocktail2, tags.get(3));  //쓴맛
		CocktailTag.associate(cocktail2, tags.get(4));  //탄산
		CocktailTag.associate(cocktail2, tags.get(5));  //멜론

		CocktailTag.associate(cocktail3, tags.get(2));  //신맛
		CocktailTag.associate(cocktail3, tags.get(3));  //쓴맛
		CocktailTag.associate(cocktail3, tags.get(7));  //초코

		CocktailTag.associate(cocktail4, tags.get(3));  //쓴맛
		CocktailTag.associate(cocktail4, tags.get(5));  //멜론
		CocktailTag.associate(cocktail4, tags.get(8));  //코코넛
		CocktailTag.associate(cocktail4, tags.get(9));  //연인과 함께

		cocktails = Arrays.asList(cocktail1, cocktail2, cocktail3, cocktail4);

		abvAnswer = new AbvAnswer(100, 0);
		moodAnswer = Collections.emptyList();
		preferenceAnswers = Collections.emptyList();
		nonPreferenceAnswers = Collections.emptyList();
		flavorAnswer = new FlavorAnswer(UserPreferenceAnswer.SOSO, UserPreferenceAnswer.SOSO,
			UserPreferenceAnswer.SOSO);
	}

	@DisplayName("사용자의 추천 질문 응답에 맞게 적절한 칵테일을 추천한다.")
	@Test
	void recommendCocktails() {
		AbvAnswer abvAnswer = new AbvAnswer(20, 0);
		List<TagPreferenceAnswer> moodAnswer = Collections.singletonList(
			new TagPreferenceAnswer(9L, UserPreferenceAnswer.YES)
		);
		List<TagPreferenceAnswer> preferenceAnswers = Collections.singletonList(
			new TagPreferenceAnswer(5L, UserPreferenceAnswer.YES)
		);
		List<TagPreferenceAnswer> nonPreferenceAnswers = Arrays.asList(
			new TagPreferenceAnswer(8L, UserPreferenceAnswer.NO),
			new TagPreferenceAnswer(7L, UserPreferenceAnswer.SOSO)
		);
		FlavorAnswer flavorAnswer = new FlavorAnswer(UserPreferenceAnswer.YES, UserPreferenceAnswer.SOSO,
			UserPreferenceAnswer.NO);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		List<Cocktail> recommendedCocktails = filteringAndScoringRecommendService.recommend(cocktails,
			entityMapper,
			recommendRequest);

		Assertions.assertThat(recommendedCocktails).isNotEmpty();
	}

	@DisplayName("지정한 도수에 맞게 칵테일을 필터링한다.")
	@Test
	void recommendCocktailsWithinAbv() {
		AbvAnswer abvAnswer = new AbvAnswer(10, 0);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(filteringAndScoringRecommendService.recommend(cocktails, entityMapper, recommendRequest)).extracting(
			"name")
			.contains("a");
	}

	@DisplayName("필터링 대상 태그를 이용해 칵테일을 필터링한다.")
	@Test
	void recommendCocktailsWithNonPreference() {
		List<TagPreferenceAnswer> nonPreferenceAnswers = Collections.singletonList(
			new TagPreferenceAnswer(8L, UserPreferenceAnswer.NO));

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(filteringAndScoringRecommendService.recommend(cocktails, entityMapper, recommendRequest)).extracting(
			"name")
			.contains("b", "d");
	}

	@DisplayName("사용자의 선호를 이용해 추천 칵테일을 정렬하고 상위 3개의 칵테일 보여준다.")
	@Test
	void recommendCocktailsWithPreference() {
		List<TagPreferenceAnswer> preferenceAnswers = Arrays.asList(
			new TagPreferenceAnswer(5L, UserPreferenceAnswer.YES),
			new TagPreferenceAnswer(6L, UserPreferenceAnswer.YES),
			new TagPreferenceAnswer(11L, UserPreferenceAnswer.NO)
		);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(filteringAndScoringRecommendService.recommend(cocktails, entityMapper, recommendRequest)).extracting(
			"name")
			.containsExactlyInAnyOrder("b", "d", "c");
	}

	@DisplayName("사용자의 분위기 선호를 이용해 추천 칵테일을 정렬하고 상위 3개의 칵테일 보여준다.")
	@Test
	void recommendCocktailsWithMoodPreference() {
		List<TagPreferenceAnswer> moodAnswer = Collections.singletonList(
			new TagPreferenceAnswer(10L, UserPreferenceAnswer.NO));

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(filteringAndScoringRecommendService.recommend(cocktails, entityMapper, recommendRequest)).extracting(
			"name")
			.isNotEmpty()
			.doesNotContain("d");
	}

	@DisplayName("사용자의 맛 선호를 이용해 추천 칵테일을 정렬하고 상위 3개의 칵테일 보여준다.")
	@Test
	void recommendCocktailsWithFlavorPreference() {
		Cocktail newCocktail3 = Cocktail.builder()
			.name("c")
			.abv(30)
			.flavor(Flavor.builder()
				.bitter(true)
				.sour(true)
				.sweet(true)
				.build())
			.build();

		List<Cocktail> cocktails = Arrays.asList(cocktail1, cocktail2, newCocktail3, cocktail4);

		FlavorAnswer flavorAnswer = new FlavorAnswer(UserPreferenceAnswer.NO, UserPreferenceAnswer.NO,
			UserPreferenceAnswer.NO);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(filteringAndScoringRecommendService.recommend(cocktails, entityMapper, recommendRequest)).extracting(
			"name")
			.isNotEmpty()
			.doesNotContain("c");
	}
}