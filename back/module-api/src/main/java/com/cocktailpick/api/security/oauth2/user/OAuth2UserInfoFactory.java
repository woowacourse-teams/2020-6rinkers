package com.cocktailpick.api.security.oauth2.user;

import java.util.Map;

import com.cocktailpick.api.security.oauth2.OAuth2AuthenticationProcessingException;
import com.cocktailpick.core.user.domain.AuthProvider;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		}
		throw new OAuth2AuthenticationProcessingException(
			registrationId + " 로그인은 지원하지 않습니다.");
	}
}