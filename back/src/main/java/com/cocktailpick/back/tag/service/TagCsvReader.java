package com.cocktailpick.back.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.common.csv.CsvReader;
import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TagCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;

	private final CsvReader csvReader;

	public static TagCsvReader from(MultipartFile file) {
		return new TagCsvReader(OpenCsvReader.from(file));
	}

	public List<Tag> getTags() {
		List<String[]> tags = csvReader.readAll();
		return tags.stream()
			.map(line -> new Tag(line[NAME_COLUMN_INDEX].trim()))
			.collect(Collectors.toList());
	}
}
