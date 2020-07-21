// package com.cocktailpick.back.cocktail.service;
//
// import java.io.IOException;
// import java.io.InputStreamReader;
//
// import org.springframework.web.multipart.MultipartFile;
//
// import com.opencsv.CSVReader;
// import com.opencsv.CSVReaderBuilder;
// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
// public class CocktailCsvReader {
// 	private final CSVReader csvReader;
//
// 	public static CocktailCsvReader from(MultipartFile file) {
// 		return new CocktailCsvReader(createCsvReader(file))
// 	}
//
// 	private static CSVReader createCsvReader(MultipartFile file) {
// 		return new CSVReaderBuilder(createInputStreamReader(file))
// 			.withSkipLines(1)
// 			.build();
// 	}
//
// 	private static InputStreamReader createInputStreamReader(MultipartFile file) {
// 		try {
// 			return new InputStreamReader(file.getInputStream());
// 		} catch (IOException e) {
// 			throw new RuntimeException();
// 		}
// 	}
// }
