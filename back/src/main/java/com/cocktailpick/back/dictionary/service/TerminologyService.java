package com.cocktailpick.back.dictionary.service;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class TerminologyService {
	private final TerminologyRepository terminologyRepository;

	public Long save(Terminology terminology) {
		return terminologyRepository.save(terminology).getId();
	}
}
