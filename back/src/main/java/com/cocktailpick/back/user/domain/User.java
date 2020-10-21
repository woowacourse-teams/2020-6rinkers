package com.cocktailpick.back.user.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.cocktailpick.back.common.domain.BaseTimeEntity;
import com.cocktailpick.back.favorite.domain.Favorite;
import com.cocktailpick.back.favorite.domain.Favorites;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
	@SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	private String imageUrl;

	@Column(nullable = false)
	private Boolean emailVerified = false;

	private String password;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	private Role role;

	@Embedded
	private Favorites favorites = Favorites.empty();

	private boolean deleted;

	@Builder
	public User(Long id, String name, String email, String imageUrl, Boolean emailVerified, String password,
		AuthProvider provider, String providerId, Role role,
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

	public void deleteFavorite(Long cocktailId) {
		favorites.deleteFavorite(cocktailId);
	}

	public boolean isDuplicated(Favorite favorite) {
		return favorites.isDuplicated(favorite);
	}

	public List<Long> findFavoriteCocktailIds() {
		return favorites.findFavoriteCocktailIds();
	}

	public String roleName() {
		return role.name();
	}
}
