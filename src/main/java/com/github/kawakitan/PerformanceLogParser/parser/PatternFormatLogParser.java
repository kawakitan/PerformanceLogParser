package com.github.kawakitan.PerformanceLogParser.parser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kawakitan.PerformanceLogParser.log.Log;

public class PatternFormatLogParser extends AbstractLogParser {

	private static final Logger logger = LoggerFactory.getLogger(PatternFormatLogParser.class);

	private Pattern pattern;

	private int groupDate;
	private int groupUser;
	private int groupFunction;
	private int groupTime;

	private SimpleDateFormat dateFormat;

	public PatternFormatLogParser() {
	}

	public PatternFormatLogParser(final File... files) {
		super(files);
	}

	public void setPattern(final String format) {
		pattern = Pattern.compile(format);
	}

	public void setDateGroup(final int group) {
		groupDate = group;
	}

	public void setUserGroup(final int group) {
		groupUser = group;
	}

	public void setFunctionGroup(final int group) {
		groupFunction = group;
	}

	public void setTimeGroup(final int group) {
		groupTime = group;
	}

	public void setDateFormat(final SimpleDateFormat format) {
		this.dateFormat = format;
	}

	@Override
	protected Log doParse(final String record, final long line, final File file) {
		MyLog log = null;

		final Matcher m = pattern.matcher(record);

		if (m.find()) {
			final String strDate = m.group(groupDate);
			final String strUser = m.group(groupUser);
			final String strFunction = m.group(groupFunction);
			final String strTime = m.group(groupTime);

			try {
				final Date date = dateFormat.parse(strDate);
				final String user = strUser;
				final String function = strFunction;
				final Long time = Long.parseLong(strTime);

				log = new MyLog(date, user, function, time);
			} catch (ParseException ex) {
				logger.error("Date format error.", ex);
			}
		} else {
			System.out.println("unmatch");
		}
		return log;
	}

	private class MyLog implements Log {
		private Date date;
		private String user;
		private String function;
		private Long time;

		public MyLog(final Date date, final String user, final String function, final Long time) {
			this.date = date;
			this.user = user;
			this.function = function;
			this.time = time;
		}

		@Override
		public Date getDate() {
			return date;
		}

		@Override
		public String getUser() {
			return user;
		}

		@Override
		public String getFunction() {
			return function;
		}

		@Override
		public Long getTime() {
			return time;
		}

	}
}
