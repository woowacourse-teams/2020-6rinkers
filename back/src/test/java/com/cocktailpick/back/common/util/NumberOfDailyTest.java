package com.cocktailpick.back.common.util;

import static com.cocktailpick.back.common.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.cocktailpick.back.common.domain.DailyDate;

class NumberOfDailyTest {
	@DisplayName("같은 년, 월, 일을 가진 DailyDate는 랜덤 값 생성 시 같은 숫자가 생성되는 지 확인한다.")
	@ParameterizedTest
	@CsvSource(value = {"2020,7,27,23,59", "2020,7,27,0,1"})
	void generateBy(int year, int month, int date, int hour, int minute) {
		Date sixOClock = createDate(year, month, date, hour, minute);

		assertThat(NumberOfDaily.generateBy(DailyDate.of(sixOClock)))
			.isEqualTo(NumberOfDaily.generateBy(DailyDate.of(createDate(2020, 7, 27, 0, 0))));
	}
}