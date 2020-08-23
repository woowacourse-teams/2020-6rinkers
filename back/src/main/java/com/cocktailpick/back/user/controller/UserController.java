package com.cocktailpick.back.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.security.UserPrincipal;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserController {

	private UserRepository userRepository;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userRepository.findById(userPrincipal.getId())
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}
}
