package com.cocktailpick.core.cocktail.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Flavor {
	private boolean sweet;

	private boolean sour;

	private boolean bitter;

	@Builder
	public Flavor(boolean sweet, boolean sour, boolean bitter) {
		this.sweet = sweet;
		this.sour = sour;
		this.bitter = bitter;
	}
}
