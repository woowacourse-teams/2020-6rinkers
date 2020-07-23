package com.cocktailpick.back.common.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvReader implements CsvReader {
	private final CSVReader csvReader;

	public static OpenCsvReader from(MultipartFile file) {
		return new OpenCsvReader(createCsvReader(file));
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

	@Override
	public List<String[]> readAll() {
		try {
			return csvReader.readAll();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
