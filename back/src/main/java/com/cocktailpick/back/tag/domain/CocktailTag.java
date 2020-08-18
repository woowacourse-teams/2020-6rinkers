package com.cocktailpick.back.tag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class CocktailTag extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cocktail_tag_sequence_gen")
	@SequenceGenerator(name = "cocktail_tag_sequence_gen", sequenceName = "cocktail_tag_sequence")
	private Long id;

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

	public boolean containsTag(Tag tag) {
		return this.tag.isSameName(tag);
	}
}
