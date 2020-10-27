package com.cocktailpick.api.user;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.core.user.dto.AuthResponse;
import com.cocktailpick.core.user.dto.LoginRequest;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		Long userId = authService.registerUser(signUpRequest);
		return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
	}
}