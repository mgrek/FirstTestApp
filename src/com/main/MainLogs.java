package com.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainLogs implements Logs {

	private static Logger log;

	public MainLogs() {
		MainLogs.log = LogManager.getLogger(MainLogs.class.getName());
	}

	@Override
	public void debug(Object log) {
		// TODO Auto-generated method stub
		MainLogs.log.debug(log);
	}

	@Override
	public void info(Object log) {
		// TODO Auto-generated method stub
		MainLogs.log.info(log);
	}

	@Override
	public void error(Object log) {
		// TODO Auto-generated method stub
		MainLogs.log.error(log);
	}
}