package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cocktailpick.back.recipe.domain.RecipeItem;
import com.cocktailpick.back.recipe.domain.RecipeItemRepository;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;

@DataJpaTest
public class CocktailRepositoryTest {
	@Autowired
	private CocktailRepository cocktailRepository;

	@Autowired
	private RecipeItemRepository recipeItemRepository;

	@Autowired
	private TagRepository tagRepository;

	private Tag tag;

	private Flavor flavor;

	private Cocktail blueHawaii;

	@BeforeEach
	void setUp() {
		tag = new Tag("두강맛");

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

	@Test
	void delete() {
		tagRepository.save(tag);

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
