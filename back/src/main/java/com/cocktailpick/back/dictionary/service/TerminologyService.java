package com.cocktailpick.back.dictionary.service;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.dictionary.domain.Terminology;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class TerminologyService {

	public Long save(Terminology terminology) {
		return 1L;
	}
}
