package com.github.kawakitan.PerformanceLogParser.analyse;

import com.github.kawakitan.PerformanceLogParser.log.Log;

public interface LogAnalyse {

	void analyse(Log log);

	void report();
}
