package com.cocktailpick.core.util.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvReader implements CsvReader {
	private final CSVReader csvReader;

	public static OpenCsvReader from(InputStream inputStream) {
		return new OpenCsvReader(createCsvReader(inputStream));
	}

	private static CSVReader createCsvReader(InputStream inputStream) {
		return new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
			.withSkipLines(1)
			.build();
	}

	@Override
	public List<String[]> readAll() {
		try {
			return csvReader.readAll();
		} catch (IOException e) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
