package hrw.swenpr.bomberman.server;

import java.util.Date;

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
	private static final String levelStrings[] = {"INFO", "WARN", "ERR"};
	
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
	 * Private constructor because {@link LogMessage} is implemented
	 * as singleton
	 * 
	 * @author Lukas Jarosch
	 * 
	 * @param The log severity
	 * @param The log message
	 */
	private LogMessage(int level, String logMessage) {
		timestamp = new Date();
		message = logMessage.trim();
		severity = levelStrings[level];
		
		System.out.println("[" + severity + "] " + timestamp.toString() + " " + message);
	}
}
