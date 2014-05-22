package hrw.swenpr.bomberman.server;

import hrw.swenpr.bomberman.server.view.MainPanel;

import java.util.Date;

import sun.util.logging.resources.logging;

public class LogMessage {
	
	/**
	 * The logging severity levels
	 */
	public enum LEVEL {
		INFORMATION,
		WARNING,
		ERROR,
	};
	
	/**
	 * The string representations of the {@link LEVEL}
	 */
	private static final String levelStrings[] = {"INFO", "WARNING", "ERROR"};
	
	/**
	 * The timestamp of the log
	 */
	private Date timestamp;
	
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
	 * Private constructor because {@link LogMessage} is implemented
	 * as singleton
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @param The log severity
	 * @param The log message
	 */
	@SuppressWarnings("deprecation")
	public LogMessage(LogMessage.LEVEL level, String logMessage) {
		String timestampString;
		
		// Gather data and build string
		timestamp = new Date();
		timestampString = timestamp.getHours() + ":" + timestamp.getMinutes() + ":" + timestamp.getSeconds();
		message = logMessage.trim();
		severity = levelStrings[level.ordinal()];
		
		logString = timestampString + " - " + message + " (" + severity + ")\n";
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
