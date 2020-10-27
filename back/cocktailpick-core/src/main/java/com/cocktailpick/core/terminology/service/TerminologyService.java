package com.cocktailpick.core.terminology.service;

import static com.cocktailpick.core.common.exceptions.ErrorCode.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.cocktailpick.core.terminology.domain.Terminology;
import com.cocktailpick.core.terminology.domain.TerminologyRepository;
import com.cocktailpick.core.terminology.dto.TerminologyResponse;
import com.cocktailpick.core.util.csv.OpenCsvReader;
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
	public Long save(Terminology terminology) {
		Optional<Terminology> persistTerminology = terminologyRepository.findByName(terminology.getName());
		if (persistTerminology.isPresent()) {
			throw new InvalidValueException(TERMINOLOGY_DUPLICATED);
		}
		return terminologyRepository.save(terminology).getId();
	}

	@Transactional
	public void saveAll(MultipartFile file) {
		TerminologyCsvReader terminologyCsvReader = new TerminologyCsvReader(OpenCsvReader.from(file));
		List<Terminology> terminologies = terminologyCsvReader.getTerminologies();

		terminologyRepository.saveAll(terminologies);
	}

	@Transactional
	public void update(Terminology terminology, Long id) {
		Terminology persistTerminology = terminologyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(TERMINOLOGY_NOT_FOUND));

		persistTerminology.update(terminology);
	}

	@Transactional
	public void delete(Long id) {
		terminologyRepository.deleteById(id);
	}
}
