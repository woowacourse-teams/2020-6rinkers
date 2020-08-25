package com.cocktailpick.back.common;

import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.cocktailpick.back.security.UserPrincipal;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		List<GrantedAuthority> authorities = Collections.
			singletonList(new SimpleGrantedAuthority(customUser.roles().name()));

		UserPrincipal principal = new UserPrincipal(customUser.id(), customUser.email(), null, authorities);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password",
			principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}
