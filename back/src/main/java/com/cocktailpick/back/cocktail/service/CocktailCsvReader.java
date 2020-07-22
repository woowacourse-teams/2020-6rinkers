package com.cocktailpick.back.cocktail.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.common.csv.CsvReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailCsvReader {
	private static final String TRUE_VALUE = "1";

	private static final int NAME_INDEX = 0;
	private static final int ABV_INDEX = 1;
	private static final int DESCRIPTION_INDEX = 2;
	private static final int ORIGIN_INDEX = 3;
	private static final int IMAGE_URL_INDEX = 4;
	private static final int SWEET_INDEX = 6;
	private static final int SOUR_INDEX = 7;
	private static final int BITTER_INDEX = 8;

	private static final int TAG_INDEX = 5;
	private static final int LIQUOR_INDEX = 9;
	private static final int LIQUOR_QUANTITY_INDEX = 10;
	private static final int SPECIAL_INDEX = 12;
	private static final int SPECIAL_QUANTITY_INDEX = 13;

	private final CsvReader csvReader;

	public List<CocktailRequest> getCocktailRequests() {
		List<String[]> lines = csvReader.readAll();

		return lines.stream()
			.map(this::createCocktailRequest)
			.collect(Collectors.toList());
	}

	private CocktailRequest createCocktailRequest(String[] line) {
		return CocktailRequest.builder()
			.name(line[NAME_INDEX])
			.abv(Double.parseDouble(line[ABV_INDEX]))
			.description(line[DESCRIPTION_INDEX])
			.origin(line[ORIGIN_INDEX])
			.imageUrl(line[IMAGE_URL_INDEX])
			.tag(splitAndTrim(line[TAG_INDEX]))
			.sweet(TRUE_VALUE.equals(line[SWEET_INDEX]))
			.sour(TRUE_VALUE.equals(line[SOUR_INDEX]))
			.bitter(TRUE_VALUE.equals(line[BITTER_INDEX]))
			.liquor(splitAndTrim(line[LIQUOR_INDEX]))
			.liquorQuantity(splitAndTrim(line[LIQUOR_QUANTITY_INDEX]))
			.special(splitAndTrim(line[SPECIAL_INDEX]))
			.specialQuantity(splitAndTrim(line[SPECIAL_QUANTITY_INDEX]))
			.build();
	}

	private List<String> splitAndTrim(String input) {
		return Arrays.stream(input.split(CsvReader.DELIMITER))
			.map(String::trim)
			.filter(StringUtils::isNotBlank)
			.collect(Collectors.toList());
	}
}
