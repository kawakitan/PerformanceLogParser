package com.github.kawakitan.PerformanceLogParser.log;

import java.util.Date;

public interface Log {

	Date getDate();

	String getUser();

	String getFunction();

	Long getTime();

}
