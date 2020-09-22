package com.cocktailpick.core.terminology.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminologyRepository extends JpaRepository<Terminology, Long> {
	Optional<Terminology> findByName(String name);
}
