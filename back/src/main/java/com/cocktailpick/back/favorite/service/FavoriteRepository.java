package com.cocktailpick.back.favorite.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktailpick.back.favorite.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
