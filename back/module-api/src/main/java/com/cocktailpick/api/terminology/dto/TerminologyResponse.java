package com.cocktailpick.api.terminology.dto;

import java.util.ArrayList;
import java.util.List;

import com.cocktailpick.core.terminology.domain.Terminology;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TerminologyResponse {
	private Long id;

	private String name;

	private String terminologyType;

	private String description;

	private String imageUrl;

	public static TerminologyResponse of(Terminology terminology) {
		return new TerminologyResponse(terminology.getId(), terminology.getName(),
			terminology.getTerminologyType().getTypeName(), terminology.getDescription(), terminology.getImageUrl());
	}

	public static List<TerminologyResponse> listOf(List<Terminology> terminologies) {
		List<TerminologyResponse> terminologyResponses = new ArrayList<>();
		for (Terminology terminology : terminologies) {
			terminologyResponses.add(TerminologyResponse.of(terminology));
		}
		return terminologyResponses;
	}
}
