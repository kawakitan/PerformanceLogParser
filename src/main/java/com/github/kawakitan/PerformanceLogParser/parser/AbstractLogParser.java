package com.github.kawakitan.PerformanceLogParser.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kawakitan.PerformanceLogParser.log.Log;

public abstract class AbstractLogParser implements LogParser {

	private static final Logger logger = LoggerFactory.getLogger(AbstractLogParser.class);

	private final List<LogParserListener> listeners;

	private Charset charset;

	private final List<File> files;

	public AbstractLogParser() {
		listeners = new ArrayList<>();
		files = new ArrayList<File>();
		charset = Charset.forName(System.getProperty("file.encoding"));
	}

	public AbstractLogParser(final File... files) {
		this();

		for (File f : files) {
			this.files.add(f);
		}
	}

	public final void addLogFile(final File file) {
		files.add(file);
	}

	@Override
	public final synchronized void addLogParserListener(final LogParserListener listener) {
		listeners.add(listener);
	}

	@Override
	public final void parse() {

		BufferedReader reader = null;
		try {

			for (File file : files) {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				long line = 0;

				String record = null;
				while (null != (record = reader.readLine())) {
					if (0 < record.length()) {
						final Log log = doParse(record, line, file);

						if (null != log) {
							for (LogParserListener l : listeners) {
								l.find(log);
							}
						}
					}
					line++;
				}

				reader.close();
				reader = null;
			}

		} catch (IOException ex) {
			logger.warn("File IO error.", ex);

		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException ex) {
					logger.warn("File close error.", ex);
				}
			}
		}

	}

	protected abstract Log doParse(String record, long line, File file);
}
