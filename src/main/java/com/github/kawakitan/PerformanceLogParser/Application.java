package com.github.kawakitan.PerformanceLogParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.github.kawakitan.PerformanceLogParser.analyse.HourLogAnalyse;
import com.github.kawakitan.PerformanceLogParser.analyse.LogAnalyse;
import com.github.kawakitan.PerformanceLogParser.log.Log;
import com.github.kawakitan.PerformanceLogParser.parser.LogParserListener;
import com.github.kawakitan.PerformanceLogParser.parser.PatternFormatLogParser;

public class Application {

	public static void main(final String[] args) {

		final List<LogAnalyse> analyses = new ArrayList<LogAnalyse>();
		analyses.add(new HourLogAnalyse());

		final PatternFormatLogParser parser = new PatternFormatLogParser();

		parser.addLogFile(new File("./data/test01.log"));
		parser.setPattern(
				"^([0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}) \\[user:([^\\]]*)\\] ([^\\s]+) \\[([0-9]+)ms\\]$");
		parser.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"));
		parser.setDateGroup(1);
		parser.setUserGroup(2);
		parser.setFunctionGroup(3);
		parser.setTimeGroup(4);

		parser.addLogParserListener(new LogParserListener() {
			@Override
			public void find(final Log log) {
				for (LogAnalyse analyse : analyses) {
					analyse.analyse(log);
				}
			}
		});

		parser.parse();

		for (LogAnalyse analyse : analyses) {
			analyse.report();
		}
	}

}
