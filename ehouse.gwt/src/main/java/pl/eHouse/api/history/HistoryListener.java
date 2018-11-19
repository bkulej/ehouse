/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.history;

import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import pl.eHouse.api.config.Settings;
import pl.eHouse.api.config.SettingsException;
import pl.eHouse.api.listeners.IInputListener;
import pl.eHouse.api.listeners.IOutputListener;
import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.message.MessageInBoot;
import pl.eHouse.api.message.MessageInSerial;
import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.MessageOutSerial;

/**
 * 
 * @author Bartek
 */
public class HistoryListener implements IOutputListener, IInputListener {
	
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATE_APPENDER = "yyyy-MM-dd";

	private Logger logger;

	public HistoryListener() throws SecurityException, IOException,
			SettingsException {
		// Format logu
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%d{"+ DATE_FORMAT + "} %m%n");
		// Rotowanie logów
		DailyRollingFileAppender appender = new DailyRollingFileAppender();
		appender.setFile(Settings.getLogFilePath());
		appender.setDatePattern("'.'" + DATE_APPENDER);
		appender.setLayout(layout);
		appender.activateOptions();
		// Utworzenie logu
		logger = Logger.getLogger(HistoryListener.class.getName());
		logger.setLevel(Level.INFO);
		logger.addAppender(appender);
	}

	/**
	 * Logowanie komunikatÃ³w przychodzacych
	 * 
	 * @param message
	 */
	public void messageOut(MessageOutSerial message) {
		logger.log(Level.INFO, message.toString());
	}

	public void messageOut(MessageOutAddress message) {
		logger.log(Level.INFO, message.toString());
	}

	public void messageOut(MessageOutBoot message) {
		logger.log(Level.INFO, message.toString());
	}

	public void messageIn(MessageInSerial message) {
		logger.log(Level.INFO, message.toString());
	}

	public void messageIn(MessageInAddress message) {
		logger.log(Level.INFO, message.toString());
	}

	public void messageIn(MessageInBoot message) {
		logger.log(Level.INFO, message.toString());
	}

	@Override
	public String getId() {
		return "HISTORY-0";
	}

}
