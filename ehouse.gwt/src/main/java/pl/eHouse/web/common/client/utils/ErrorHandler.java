package pl.eHouse.web.common.client.utils;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.PopupMessage;

public class ErrorHandler implements CometInterface {

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometErrorResponse) {
			CometErrorResponse response = (CometErrorResponse) message;
			PopupMessage.showError(LocaleFactory.getMessages().Setup_error()
					+ response.getMessage());
		}
	}

}
