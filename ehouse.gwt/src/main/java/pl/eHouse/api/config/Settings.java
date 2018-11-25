package pl.eHouse.api.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {

	private static Properties properties;
	private static String ehouseProperties;

	public static void init() throws SettingsException {
		if (properties == null) {
			// Wyszukanie zmiennej �rodowiskowej
			ehouseProperties = "/opt/ehouse/ehouse.gwt.properties"; // System.getenv("EHOUSE_PATH");
			Logger.getLogger(Settings.class.getName()).log(Level.INFO, "ehouseProperties=" + ehouseProperties);
			try {
				Properties tmp = new Properties();
				tmp.load(new FileInputStream(ehouseProperties));
				properties = tmp;
			} catch (FileNotFoundException e) {
				throw new SettingsException(e.getMessage());
			} catch (IOException e) {
				throw new SettingsException(e.getMessage());
			}
		}
	}

	public static String getConfigFilePath() throws SettingsException {
		init();
		return properties.getProperty("config");
	}

	public static String getLogFilePath() throws SettingsException {
		init();
		return properties.getProperty("log");
	}

	public static String getUploadDirectory() throws SettingsException {
		init();
		return properties.getProperty("upload");
	}

	public static String getSocketAddress() throws SettingsException {
		init();
		return properties.getProperty("socket.address");
	}

	public static int getSocketPort() throws SettingsException {
		init();
		return Integer.parseInt(properties.getProperty("socket.port"));
	}

}
