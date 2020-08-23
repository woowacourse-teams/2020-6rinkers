package com.cocktailpick.back.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

import com.cocktailpick.back.favorite.domain.Favorite;
import com.cocktailpick.back.favorite.domain.Favorites;
import com.cocktailpick.back.common.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
	@SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	private String imageUrl;

	@Column(nullable = false)
	private Boolean emailVerified = false;

	@JsonIgnore
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	private Role role;

	@Embedded
	private Favorites favorites = Favorites.empty();

	public String getRoleName() {
		return role.name();
	}
}
