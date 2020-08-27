package com.cocktailpick.back.cocktail.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktail.domain.Flavor;
import com.cocktailpick.back.cocktail.dto.AbvAnswer;
import com.cocktailpick.back.cocktail.dto.FlavorAnswer;
import com.cocktailpick.back.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.cocktail.dto.TagPreferenceAnswer;
import com.cocktailpick.back.cocktail.vo.UserPreferenceAnswer;
import com.cocktailpick.back.favorite.domain.Favorite;
import com.cocktailpick.back.favorite.domain.Favorites;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.tag.domain.TagType;
import com.cocktailpick.back.user.domain.EmptyUser;
import com.cocktailpick.back.user.domain.User;

@ExtendWith(MockitoExtension.class)
class CocktailRecommendServiceTest {
	private CocktailRecommendService cocktailRecommendService;

	@Mock
	private CocktailRepository cocktailRepository;

	@Mock
	private TagRepository tagRepository;

	@Mock
	private FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	private Flavor flavor;

	private Cocktail cocktail1;

	private Cocktail cocktail2;

	private Cocktail cocktail3;

	private Cocktail cocktail4;

	private List<Tag> tags;

	private List<Cocktail> cocktails;

	private static Stream<Arguments> provideUsers() {
		Favorites favorites = Favorites.empty();

		Favorite favorite = new Favorite(1L, User.builder().id(1L).build(), Cocktail.builder().id(1L).build());

		favorites.addFavorite(favorite);

		User user = User.builder().id(1L).name("doo").favorites(favorites).build();

		return Stream.of(
			Arguments.of(new EmptyUser()),
			Arguments.of(user)
		);
	}

	@BeforeEach
	void setUp() {
		cocktailRecommendService = new CocktailRecommendService(cocktailRepository, tagRepository,
			filteringAndScoringRecommendService);

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

		tags = Arrays.asList(
			Tag.builder().id(1L).name("도수가 높은").tagType(TagType.ABV).build(),
			Tag.builder().id(2L).name("단맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().id(3L).name("신맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().id(4L).name("쓴맛").tagType(TagType.FLAVOR).build(),
			Tag.builder().id(5L).name("탄산").tagType(TagType.INGREDIENT).build(),
			Tag.builder().id(6L).name("멜론").tagType(TagType.INGREDIENT).build(),
			Tag.builder().id(7L).name("커피").tagType(TagType.DISLIKE).build(),
			Tag.builder().id(8L).name("초코").tagType(TagType.DISLIKE).build(),
			Tag.builder().id(9L).name("코코넛").tagType(TagType.INGREDIENT).build(),
			Tag.builder().id(10L).name("우유").tagType(TagType.DISLIKE).build(),
			Tag.builder().id(11L).name("연인과 함께").tagType(TagType.CONCEPT).build(),
			Tag.builder().id(12L).name("레몬").tagType(TagType.INGREDIENT).build());

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
	}

	@DisplayName("사용자에게 칵테일을 추찬한다.")
	@ParameterizedTest
	@MethodSource("provideUsers")
	void recommend(User user) {
		when(cocktailRepository.findAll()).thenReturn(cocktails);
		when(tagRepository.findAll()).thenReturn(tags);
		when(filteringAndScoringRecommendService.recommend(anyList(), any(), any())).thenReturn(
			Arrays.asList(cocktail2, cocktail4));

		AbvAnswer abvAnswer = new AbvAnswer(100, 0);
		List<TagPreferenceAnswer> moodAnswer = Collections.emptyList();
		List<TagPreferenceAnswer> preferenceAnswers = Collections.emptyList();
		List<TagPreferenceAnswer> nonPreferenceAnswers = Collections.singletonList(
			new TagPreferenceAnswer(8L, UserPreferenceAnswer.NO));
		FlavorAnswer flavorAnswer = new FlavorAnswer(UserPreferenceAnswer.SOSO, UserPreferenceAnswer.SOSO,
			UserPreferenceAnswer.SOSO);

		RecommendRequest recommendRequest = new RecommendRequest(abvAnswer, moodAnswer, flavorAnswer, preferenceAnswers,
			nonPreferenceAnswers);

		assertThat(cocktailRecommendService.recommend(recommendRequest, user.getFavorites())).isNotNull();
	}
}