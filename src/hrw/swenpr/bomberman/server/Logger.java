package hrw.swenpr.bomberman.server;

import java.util.Date;

import com.sun.org.apache.xpath.internal.axes.SelfIteratorNoPredicate;

public class Logger {
	
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
	private static final String levelString[] = {"INFO", "WARN", "ERR"};
	
	/**
	 * The timestamp of the log
	 */
	private Date timestamp;
	
	/**
	 * The actual message to log
	 */
	private String message;
	
	/**
	 * The {@link Logger} singleton instance
	 */
	private static Logger logger = null;
	
	/**
	 * Private constructor because {@link Logger} is implemented
	 * as singleton
	 * 
	 * @author Lukas Jarosch
	 */
	private Logger() {
		
	}
	
	/**
	 * Returns the {@link Logger} instance
	 * 
	 * @return The logger instance
	 */
	public static Logger getLogger() {
		if(Logger.logger == null) {
			Logger.logger = new Logger();
		}
		return Logger.logger;
	}
}
