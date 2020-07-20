package com.cocktailpick.back.tag.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.domain.Tag;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCsvReader {
	private static final int NAME_COLUMN_INDEX = 0;

	private final CSVReader csvReader;

	public static TagCsvReader from(MultipartFile file) {
		return new TagCsvReader(createCsvReader(file));
	}

	private static CSVReader createCsvReader(MultipartFile file) {
		return new CSVReaderBuilder(createInputStreamReader(file))
			.withSkipLines(1)
			.build();
	}

	private static InputStreamReader createInputStreamReader(MultipartFile file) {
		try {
			return new InputStreamReader(file.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public List<Tag> getTags() {
		try {
			List<String[]> tags = csvReader.readAll();
			return tags.stream()
				.map(line -> new Tag(line[NAME_COLUMN_INDEX]))
				.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
