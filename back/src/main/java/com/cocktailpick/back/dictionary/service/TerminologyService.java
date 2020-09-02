package com.cocktailpick.back.dictionary.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyRepository;
import com.cocktailpick.back.dictionary.dto.TerminologyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class TerminologyService {
	private final TerminologyRepository terminologyRepository;

	@Transactional(readOnly = true)
	public List<TerminologyResponse> findAllTerminologies() {
		return Collections.unmodifiableList(TerminologyResponse.listOf(terminologyRepository.findAll()));

	}

	public Long save(Terminology terminology) {
		return terminologyRepository.save(terminology).getId();
	}
}
