package com.cocktailpick.back.tag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.cocktailpick.back.common.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Tag extends BaseEntity {
	@Column(name = "name")
	private String name;
}
