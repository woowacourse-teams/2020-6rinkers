package com.cocktailpick.common.util;

import java.util.Calendar;
import java.util.Date;

public class Fixtures {
	public static Date createDate(int year, int month, int date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, date, hour, minute);
		return calendar.getTime();
	}
}