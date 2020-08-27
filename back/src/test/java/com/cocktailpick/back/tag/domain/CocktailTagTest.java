package com.cocktailpick.back.tag.domain;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.Flavor;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CocktailTagTest {
	private Cocktail cocktail;
	private Tag tag;

	@BeforeEach
	void setUp() {
		Flavor flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();

		cocktail = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();

		tag = Tag.builder().name("to-doo").tagType(TagType.CONCEPT).build();
	}

	@DisplayName("cocktail과 tag를 연결한다.")
	@Test
	void associate() {
		CocktailTag.associate(cocktail, tag);

		assertThat(cocktail.getTags()).hasSize(1);
	}

	@DisplayName("Cocktail setter 편의 메소드가 작동하는 지 확인한다.")
	@Test
	void setCocktail() {
		CocktailTag cocktailTag = new CocktailTag();
		cocktailTag.setCocktail(cocktail);

		assertThat(cocktail.getCocktailTags())
			.extracting("cocktailTags")
			.asInstanceOf(InstanceOfAssertFactories.LIST)
			.hasSize(1);
	}

	@DisplayName("CocktailTag의 태그 이름이 같은 지 확인한다.")
	@Test
	void isSameNameWith() {
		CocktailTag tag1 = new CocktailTag();
		CocktailTag tag2 = new CocktailTag();

		tag1.setTag(tag);
		tag2.setTag(Tag.builder().name("will").tagType(TagType.CONCEPT).build());

		assertThat(tag1.isSameNameWith(tag2)).isFalse();
	}

	@Test
	void getTagId() {
		Tag tag = Tag.builder()
				.id(0L).build();

		CocktailTag cocktailTag = new CocktailTag();
		cocktailTag.setTag(tag);

		assertThat(cocktailTag.tagId()).isEqualTo(0L);
	}
}