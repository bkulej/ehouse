package pl.eHouse.web.common.client.locale;


import com.google.gwt.core.client.GWT;

public class LocaleFactory {
	
	private static IMessages messages;
	private static ILabels labels;
	
	public static IMessages getMessages() {
		if(messages == null) {
			messages = (IMessages)GWT.create(IMessages.class);
		}
		return messages;
	}

	public static ILabels getLabels() {
		if(labels == null) {
			labels = (ILabels)GWT.create(ILabels.class);
		}
		return labels;
	}
}
