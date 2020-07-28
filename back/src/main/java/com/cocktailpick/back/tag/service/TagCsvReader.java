package com.cocktailpick.back.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.common.csv.CsvReader;
import com.cocktailpick.back.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TagCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;

	private final CsvReader csvReader;

	public List<Tag> getTags() {
		List<String[]> tags = csvReader.readAll();
		return tags.stream()
			.map(line -> new Tag(line[NAME_COLUMN_INDEX].trim()))
			.collect(Collectors.toList());
	}
}
