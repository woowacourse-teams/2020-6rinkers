package com.cocktailpick.back.common.util;

import java.util.Random;

import com.cocktailpick.back.common.domain.DailyDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberOfDaily {
	public static long generateBy(DailyDate dailyDate) {
		int randomNumber = new Random(dailyDate.getTime()).nextInt();
		return Math.abs(randomNumber);
	}
}