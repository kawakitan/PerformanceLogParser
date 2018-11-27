package com.github.kawakitan.PerformanceLogParser.parser;

public interface LogParser {

	void addLogParserListener(LogParserListener listener);

	void parse();
}
