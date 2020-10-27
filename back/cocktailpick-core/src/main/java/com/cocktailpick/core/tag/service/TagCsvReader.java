package com.cocktailpick.core.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.util.csv.CsvReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TagCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;
	private static final int TAG_TYPE_COLUMN_INDEX = 1;

	private final CsvReader csvReader;

	public List<Tag> getTags() {
		List<String[]> tags = csvReader.readAll();
		return tags.stream()
			.map(line -> Tag.builder()
				.name(line[NAME_COLUMN_INDEX].trim())
				.tagType(TagType.of(line[TAG_TYPE_COLUMN_INDEX].trim()))
				.build())
			.collect(Collectors.toList());
	}
}
