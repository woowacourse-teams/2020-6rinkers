package com.cocktailpick.back.tag.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.cocktailpick.back.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag extends BaseEntity {
	private String tagName;

	@OneToMany(mappedBy = "tag")
	private List<CocktailTag> cocktailTags;
}
