package com.cocktailpick.api.csv;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.terminology.dto.TerminologyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TerminologyCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;
	private static final int TERMINOLOGY_TYPE_COLUMN_INDEX = 1;
	private static final int DESCRIPTION_COLUMN_INDEX = 2;
	private static final int IMAGE_URL_COLUMN_INDEX = 3;

	private final CsvReader csvReader;

	public List<TerminologyRequest> getTerminologyRequests() {
		List<String[]> terminologies = csvReader.readAll();
		return terminologies.stream()
			.map(line -> TerminologyRequest.builder()
				.name(line[NAME_COLUMN_INDEX].trim())
				.terminologyType(line[TERMINOLOGY_TYPE_COLUMN_INDEX].trim())
				.description(line[DESCRIPTION_COLUMN_INDEX].trim())
				.imageUrl(line[IMAGE_URL_COLUMN_INDEX].trim())
				.build())
			.collect(Collectors.toList());
	}
}
