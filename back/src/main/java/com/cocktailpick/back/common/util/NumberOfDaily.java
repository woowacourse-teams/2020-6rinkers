package com.cocktailpick.back.common.util;

import java.util.Random;

import com.cocktailpick.back.common.domain.DailyDate;

public class NumberOfDaily {
	public static long generateBy(DailyDate dailyDate) {
		return new Random(dailyDate.getTime()).nextLong();
	}
}