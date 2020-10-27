package com.cocktailpick.core.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {
	@NotBlank
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}