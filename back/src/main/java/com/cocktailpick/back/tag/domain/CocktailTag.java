package com.cocktailpick.back.tag.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class CocktailTag extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "cocktail_id")
	private Cocktail cocktail;

	@ManyToOne
	@JoinColumn(name = "tag_id")
	private Tag tag;

	public static CocktailTag associate(Cocktail cocktail, Tag tag) {
		CocktailTag cocktailTag = new CocktailTag();
		cocktailTag.setTag(tag);
		cocktailTag.setCocktail(cocktail);

		return cocktailTag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setCocktail(Cocktail cocktail) {
		this.cocktail = cocktail;
		cocktail.getCocktailTags().addCocktailTag(this);
	}

	public boolean isSameNameWith(CocktailTag cocktailTag) {
		return this.tag.getName().equals(cocktailTag.tag.getName());
	}
}
