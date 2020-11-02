package com.cocktailpick.core.terminology.service;

import static com.cocktailpick.core.common.exceptions.ErrorCode.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.cocktailpick.core.terminology.domain.Terminology;
import com.cocktailpick.core.terminology.domain.TerminologyRepository;
import com.cocktailpick.core.terminology.dto.TerminologyRequest;
import com.cocktailpick.core.terminology.dto.TerminologyResponse;
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

	@Transactional(readOnly = true)
	public TerminologyResponse findTerminology(Long id) {
		return TerminologyResponse.of(
			terminologyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TERMINOLOGY_NOT_FOUND)));
	}

	@Transactional
	public Long save(TerminologyRequest terminologyRequest) {
		Terminology terminology = terminologyRequest.toTerminology();
		Optional<Terminology> existed = terminologyRepository.findByName(terminology.getName());
		if (existed.isPresent()) {
			throw new InvalidValueException(TERMINOLOGY_DUPLICATED);
		}
		return terminologyRepository.save(terminology).getId();
	}

	@Transactional
	public void saveAll(List<TerminologyRequest> terminologyRequests) {
		terminologyRepository.saveAll(
			terminologyRequests.stream().map(TerminologyRequest::toTerminology).collect(Collectors.toList()));
	}

	@Transactional
	public void update(TerminologyRequest terminologyRequest, Long id) {
		Terminology persistTerminology = terminologyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(TERMINOLOGY_NOT_FOUND));

		persistTerminology.update(terminologyRequest.toTerminology());
	}

	@Transactional
	public void delete(Long id) {
		terminologyRepository.deleteById(id);
	}
}
