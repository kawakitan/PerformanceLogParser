package com.github.kawakitan.PerformanceLogParser.parser;

import com.github.kawakitan.PerformanceLogParser.log.Log;

public interface LogParserListener {

	void find(Log log);
}
