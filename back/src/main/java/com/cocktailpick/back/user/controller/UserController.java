package com.cocktailpick.back.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.security.UserPrincipal;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import com.cocktailpick.back.user.dto.UserResponse;
import com.cocktailpick.back.user.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return ResponseEntity.ok(userService.findMe(userPrincipal.getId()));
	}
}
