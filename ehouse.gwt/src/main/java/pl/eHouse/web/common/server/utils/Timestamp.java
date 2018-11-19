package pl.eHouse.web.common.server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timestamp {
	
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public static Date getTime() {
		return Calendar.getInstance().getTime();
	}
	
	public static String getTextTime() {
		return timeFormat.format(Calendar.getInstance().getTime());
	}
	
	public static String getTextTime(Date date) {
		return timeFormat.format(date);
	}
	
	public static String getTextTimestamp() {
		return timestampFormat.format(Calendar.getInstance().getTime());
	}
	
	public static String getTextTimestamp(Date date) {
		return timestampFormat.format(date);
	}

}
