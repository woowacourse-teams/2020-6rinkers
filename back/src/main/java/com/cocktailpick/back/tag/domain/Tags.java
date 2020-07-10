package com.cocktailpick.back.tag.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {
	@OneToMany(mappedBy = "cocktail")
	private List<Tag> tags;
}
