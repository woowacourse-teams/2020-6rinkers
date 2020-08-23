package com.cocktailpick.back.user.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.cocktailpick.back.favorite.domain.Favorites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "users", uniqueConstraints = {
	@UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Email
	@Column(nullable = false)
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

	@Builder
	public User(Long id, String name, @Email String email, String imageUrl, Boolean emailVerified, String password,
		@NotNull AuthProvider provider, String providerId, Role role,
		Favorites favorites) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
		this.emailVerified = emailVerified;
		this.password = password;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
		this.favorites = favorites;
	}

	public String getRoleName() {
		return role.name();
	}

	public void deleteFavorite(Long cocktailId) {
		favorites.deleteFavorite(cocktailId);
	}
}
