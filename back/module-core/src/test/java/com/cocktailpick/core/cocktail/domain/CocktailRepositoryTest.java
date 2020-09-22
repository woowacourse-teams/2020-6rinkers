package com.cocktailpick.core.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cocktailpick.core.recipe.domain.RecipeItem;
import com.cocktailpick.core.recipe.domain.RecipeItemRepository;
import com.cocktailpick.core.tag.domain.CocktailTag;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagRepository;
import com.cocktailpick.core.tag.domain.TagType;

@DataJpaTest
public class CocktailRepositoryTest {
	@Autowired
	private CocktailRepository cocktailRepository;

	@Autowired
	private RecipeItemRepository recipeItemRepository;

	@Autowired
	private TagRepository tagRepository;

	private Tag tag;

	private Cocktail blueHawaii;

	private Flavor flavor;

	@BeforeEach
	void setUp() {
		tag = Tag.builder()
			.name("두강맛")
			.tagType(TagType.FLAVOR)
			.build();

		tagRepository.save(tag);

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
	}

	@DisplayName("칵테일을 특정 크기만큼 조회한다.")
	@Test
	void findByIdGreaterThan() {
		RecipeItem martiniRecipe = RecipeItem.builder()
			.ingredient("gin")
			.quantity("30ml")
			.build();

		RecipeItem ginTonicRecipe = RecipeItem.builder()
			.ingredient("gin")
			.quantity("45ml")
			.build();

		Cocktail martini = Cocktail.builder()
			.abv(40)
			.description("movie 007")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("martini")
			.origin("stir don't shake")
			.build();

		Cocktail ginTonic = Cocktail.builder()
			.abv(25)
			.description("classic cocktail")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("gin tonic")
			.origin("I don't know")
			.build();

		martiniRecipe.setCocktail(martini);
		CocktailTag.associate(martini, tag);
		ginTonicRecipe.setCocktail(ginTonic);
		CocktailTag.associate(ginTonic, tag);

		cocktailRepository.saveAll(Arrays.asList(blueHawaii, martini, ginTonic));

		Pageable pageRequest = PageRequest.of(0, 2);
		assertThat(cocktailRepository.findByNameContainingAndIdGreaterThan("", 1, pageRequest).getContent()).hasSize(
			pageRequest.getPageSize());
	}

	@DisplayName("저장된 칵테일이 없을시 페이지로 조회할 경우 빈 문자열을 담은 Pageable 객체를 반환한다.")
	@Test
	void findByIdGreaterThan_whenNoData_returnEmptyList() {
		Pageable pageRequest = PageRequest.of(0, 2);
		assertThat(cocktailRepository.findByNameContainingAndIdGreaterThan("", 1, pageRequest).getContent()).hasSize(0);
	}

	@DisplayName("칵테일이 삭제될 경우 recipeItem도 삭제한다.")
	@Test
	void delete() {

		RecipeItem recipeItem = RecipeItem.builder()
			.ingredient("두강이")
			.quantity("두ml")
			.build();

		recipeItem.setCocktail(blueHawaii);
		CocktailTag.associate(blueHawaii, tag);

		cocktailRepository.saveAndFlush(blueHawaii);
		assertThat(recipeItemRepository.findAll()).hasSize(1);

		cocktailRepository.deleteById(blueHawaii.getId());
		assertThat(recipeItemRepository.findAll()).isEmpty();
	}
}
