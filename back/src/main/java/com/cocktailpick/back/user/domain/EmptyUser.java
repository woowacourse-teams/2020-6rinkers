package com.cocktailpick.back.user.domain;

import com.cocktailpick.back.favorite.domain.Favorites;

public class EmptyUser extends User {
	public EmptyUser() {
		super(null, null, null, null, null, null, null, null, null, Favorites.empty());
	}
}