package com.cocktailpick.api.csv;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.tag.dto.TagRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TagCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;
	private static final int TAG_TYPE_COLUMN_INDEX = 1;

	private final CsvReader csvReader;

	public List<TagRequest> getTagRequests() {
		List<String[]> tags = csvReader.readAll();
		return tags.stream()
			.map(line -> new TagRequest(line[NAME_COLUMN_INDEX].trim(), line[TAG_TYPE_COLUMN_INDEX].trim()))
			.collect(Collectors.toList());
	}
}
