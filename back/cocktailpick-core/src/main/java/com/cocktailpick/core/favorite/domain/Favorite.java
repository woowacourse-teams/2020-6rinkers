package com.cocktailpick.core.favorite.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.common.domain.BaseTimeEntity;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.cocktailpick.core.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Favorite extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_sequence_gen")
	@SequenceGenerator(
		name = "favorite_sequence_gen",
		sequenceName = "favorite_sequence"
	)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "cocktail_id")
	private Cocktail cocktail;

	public void setUser(User user) {
		if (user.isDuplicated(this)) {
			throw new InvalidValueException(ErrorCode.FAVORITE_DUPLICATED);
		}
		this.user = user;
		user.getFavorites().addFavorite(this);
	}

	public void setCocktail(Cocktail cocktail) {
		this.cocktail = cocktail;
	}

	public boolean isContainCocktail(Cocktail cocktail) {
		return this.cocktail.isSameWith(cocktail.getId());
	}

	public Long getCocktailId() {
		return this.cocktail.getId();
	}
}
