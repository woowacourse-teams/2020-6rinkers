package com.cocktailpick.back.favorite.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Favorites {
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Favorite> favorites = new ArrayList<>();

	public static Favorites empty() {
		return new Favorites();
	}
}
