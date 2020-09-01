package com.cocktailpick.back.tag.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TerminologyRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String terminologyType;
	@NotBlank
	private String description;
	@URL
	private String imageUrl;

	@Builder
	public TerminologyRequest(String name, String terminologyType, String description, String imageUrl) {
		this.name = name;
		this.terminologyType = terminologyType;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public Terminology toTerminology() {
		return Terminology.builder()
			.name(name)
			.terminologyType(TerminologyType.of(terminologyType))
			.description(description)
			.imageUrl(imageUrl)
			.build();
	}
}
