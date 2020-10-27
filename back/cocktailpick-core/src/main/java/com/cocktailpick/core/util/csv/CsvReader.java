package com.cocktailpick.core.util.csv;

import java.util.List;

public interface CsvReader {
	String DELIMITER = ",";

	List<String[]> readAll();
}
