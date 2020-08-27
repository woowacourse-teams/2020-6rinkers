package com.cocktailpick.back.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.cocktailpick.back.security.UserPrincipal;
import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.domain.User;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User user = new User();
		user.setId(customUser.id());
		user.setEmail(customUser.email());
		user.setEmailVerified(customUser.emailVerified());
		user.setImageUrl(customUser.imageUrl());
		user.setName(customUser.name());
		user.setPassword(customUser.password());
		user.setProvider(customUser.provider());
		user.setRole(customUser.roles());
		user.setProviderId(customUser.providerId());

		UserPrincipal principal = UserPrincipal.create(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, user.getPassword(),
			principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}
