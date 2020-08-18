package com.cocktailpick.back.security.oauth2.user;

import java.util.Map;

import com.cocktailpick.back.common.exceptions.OAuth2AuthenticationProcessingException;
import com.cocktailpick.back.user.domain.AuthProvider;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		}
		throw new OAuth2AuthenticationProcessingException(
			registrationId + " 로그인은 지원하지 않습니다.");
	}
}