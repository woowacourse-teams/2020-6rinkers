package com.cocktailpick.back.dictionary.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.checkerframework.common.aliasing.qual.Unique;

import com.cocktailpick.back.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Terminology extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "terminology_sequence_gen")
	@SequenceGenerator(
		name = "terminology_sequence_gen",
		sequenceName = "terminology_sequence"
	)
	private Long id;

	@Unique
	private String name;

	@Enumerated(EnumType.STRING)
	private TerminologyType terminologyType;

	@Lob
	private String description;

	@Lob
	private String imageUrl;

	@Builder
	public Terminology(Long id, String name, TerminologyType terminologyType, String description, String imageUrl) {
		this.id = id;
		this.name = name;
		this.terminologyType = terminologyType;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public Terminology update(Terminology terminology) {
		this.name = terminology.getName();
		this.terminologyType = terminology.getTerminologyType();
		this.description = terminology.getDescription();
		this.imageUrl = terminology.getImageUrl();

		return this;
	}
}
