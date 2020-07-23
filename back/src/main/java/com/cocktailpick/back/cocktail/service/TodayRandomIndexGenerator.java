package com.cocktailpick.back.cocktail.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.cocktailpick.back.common.random.RandomIndexGenerator;

@Component
public class TodayRandomIndexGenerator implements RandomIndexGenerator {
	@Override
	public int generate(long length) {
		long seed = createSeed();
		Random random = new Random(seed);
		return (int)(random.nextDouble() * length);
	}

	private long createSeed() {
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String format = simpleDateFormat.format(date);

			return simpleDateFormat.parse(format).getTime();
		} catch (ParseException e) {
			throw new RuntimeException();
		}
	}
}
