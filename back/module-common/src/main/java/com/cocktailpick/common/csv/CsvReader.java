package com.cocktailpick.common.csv;

import java.util.List;

public interface CsvReader {
	String DELIMITER = ",";

	List<String[]> readAll();
}
