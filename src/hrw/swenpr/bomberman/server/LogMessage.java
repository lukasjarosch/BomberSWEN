package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.view.MainPanel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.util.logging.resources.logging;

public class LogMessage {
	
	/**
	 * The logging severity levels
	 */
	public enum LEVEL {
		NONE,
		INFORMATION,
		WARNING,
		ERROR,
	};
	
	/**
	 * The string representations of the {@link LEVEL}
	 */
	private static final String levelStrings[] = {"", "Information", "Warning", "Error"};
	
	/**
	 * The actual message to log
	 */
	private String message;
	
	/**
	 * The string representation of the current log level
	 */
	private String severity;
	
	/**
	 * The final string which should be placed in the {@link MainPanel}
	 */
	private String logString;
	
	/**
	 * Create a new log message and prepare the logString
	 * The logstring can then be passed to any output you wish
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @param The log severity
	 * @param The log message
	 */
	public LogMessage(LogMessage.LEVEL level, String logMessage) {
		String timestampString;		
		
		// Severity string
		if(level == LEVEL.NONE) {
			severity = "";
		} else {
			severity = " (" + levelStrings[level.ordinal()] + ")";
		}
		
		// Gather data and build string		
		message = logMessage.trim();
		timestampString = new SimpleDateFormat("HH:mm:ss").format(new Date());
		logString = timestampString + " # " +  message + severity + "\n";
	}
	
	/**
	 * Returns the final log string
	 * 
	 * @return Log string
	 */
	public String getLogString() {
		return logString;
	}
}
