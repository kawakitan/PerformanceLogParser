package com.github.kawakitan.PerformanceLogParser.analyse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.kawakitan.PerformanceLogParser.log.Log;

public class HourLogAnalyse implements LogAnalyse {

	private Calendar calender;

	private List<Long> cnt;
	private List<Long> max;
	private List<Long> min;

	public HourLogAnalyse() {
		calender = Calendar.getInstance();

		cnt = new ArrayList<Long>();
		max = new ArrayList<Long>();
		min = new ArrayList<Long>();
		for (int month = 0; month < 24; month++) {
			cnt.add(Long.valueOf(0L));
			max.add(null);
			min.add(null);
		}
	}

	@Override
	public void analyse(final Log log) {
		calender.setTime(log.getDate());

		final int hour = calender.get(Calendar.HOUR_OF_DAY);

		final Long time = log.getTime();

		cnt.set(hour, cnt.get(hour) + 1);
		min.set(hour, min(min.get(hour), time));
		max.set(hour, max(max.get(hour), time));
	}

	@Override
	public void report() {
		for (int hour = 0; hour < 24; hour++) {
			System.out.println(String.format("%02d:00 %d %d %d", hour, cnt.get(hour), min.get(hour), max.get(hour)));
		}
	}

	private Long min(final Long a, final Long b) {
		if (null != a && null != b) {
			return Math.min(a, b);
		} else if (null == b) {
			return a;
		} else if (null == a) {
			return b;
		} else {
			return null;
		}
	}

	private Long max(final Long a, final Long b) {
		if (null != a && null != b) {
			return Math.max(a, b);
		} else if (null == b) {
			return a;
		} else if (null == a) {
			return b;
		} else {
			return null;
		}
	}
}
