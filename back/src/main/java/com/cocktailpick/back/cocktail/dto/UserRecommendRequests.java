package com.cocktailpick.back.cocktail.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRecommendRequests {
	private List<UserRecommendRequest> userRecommendRequests;
}
