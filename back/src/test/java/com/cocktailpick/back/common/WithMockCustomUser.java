package com.cocktailpick.back.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import com.cocktailpick.back.user.domain.Role;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	long id() default 1L;

	String email() default "a@email.com";

	Role roles() default Role.ROLE_USER;
}
