package com.cocktailpick.back.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.cocktailpick.back.security.UserPrincipal;
import com.cocktailpick.back.user.domain.User;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User user = User.builder()
			.id(customUser.id())
			.email(customUser.email())
			.emailVerified(customUser.emailVerified())
			.imageUrl(customUser.imageUrl())
			.name(customUser.name())
			.password(customUser.password())
			.provider(customUser.provider())
			.role(customUser.roles())
			.providerId(customUser.providerId())
			.build();

		UserPrincipal principal = UserPrincipal.create(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, user.getPassword(),
			principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}
