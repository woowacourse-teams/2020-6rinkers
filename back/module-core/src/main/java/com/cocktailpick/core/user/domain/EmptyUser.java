package com.cocktailpick.core.user.domain;

import com.cocktailpick.core.favorite.domain.Favorites;

public class EmptyUser extends User {
	public EmptyUser() {
		super(null, null, null, null, null, null, null, null, null, Favorites.empty());
	}
}