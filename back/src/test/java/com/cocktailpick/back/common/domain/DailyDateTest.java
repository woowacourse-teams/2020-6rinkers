package com.cocktailpick.back.common.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DailyDateTest {
	@DisplayName("입력 받은 Date 값에서 일년월을 제외한 값을 0으로 만든다.")
	@ParameterizedTest
	@CsvSource(value = {"2020,7,27,23,59", "2020,7,27,0,1"})
	void of_SameDay(int year, int month, int date, int hour, int minute) {
		Date sixOClock = createDate(year, month, date, hour, minute);

		assertThat(DailyDate.of(sixOClock).getDate())
			.isEqualTo(createDate(2020, 7, 27, 0, 0));
	}

	private Date createDate(int year, int month, int date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, date, hour, minute);
		return calendar.getTime();
	}
}