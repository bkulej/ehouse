package pl.eHouse.web.common.server.execute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Settings;
import pl.eHouse.api.history.HistoryParser;
import pl.eHouse.api.history.HistoryValue;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryMessage;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryResponse;

public class HistoryExecutor {

	public static void execute(CometFindHistoryMessage message,
			GwtAtmosphereResource resource) {
		try {
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse(message
					.getStart());
			Date stop = new SimpleDateFormat("yyyy-MM-dd").parse(message
					.getStop());
			List<HistoryValue> history = new HistoryParser(
					Settings.getLogFilePath()).find(start, stop,
					message.getAddresses(), message.getCommands());
			for (HistoryValue value : history) {
				resource.post(new CometFindHistoryResponse(value.getDate(),
						value.getType(), value.getAdd(), value.getAsd(), value
								.getId(), value.getCommand(), value.getData()));
			}
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
