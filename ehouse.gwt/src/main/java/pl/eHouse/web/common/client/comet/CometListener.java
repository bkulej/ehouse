package pl.eHouse.web.common.client.comet;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.atmosphere.gwt.client.AtmosphereListener;

import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.widgets.PopupMessage;

public class CometListener implements AtmosphereListener {

	private static Logger logger = Logger.getLogger("CometListener");
	private static CometInterface apiListener;
	private static CometInterface errorListener;
	private static CometInterface responseListener;

	public synchronized static void setApiListener(CometInterface listener) {
		apiListener = listener;
	}

	public synchronized static void setErrorListener(CometInterface listener) {
		errorListener = listener;
	}

	public synchronized static void setResponseListener(CometInterface listener) {
		CometListener.responseListener = listener;
	}

	@Override
	public void onConnected(int heartbeat, String connectionUUID) {
		logger.info("Comet client connected: " + connectionUUID);
	}

	@Override
	public void onAfterRefresh(String connectionUUID) {
		logger.info("Comet client refreshed: " + connectionUUID);
	}

	@Override
	public void onBeforeDisconnected() {
	}

	@Override
	public void onDisconnected() {
		logger.info("Comet client disconnected");
		PopupMessage.showError("Comet client disconnected");
	}

	@Override
	public void onError(Throwable exception, boolean connected) {
		logger.log(Level.SEVERE, "Comet client error: ", exception);
		PopupMessage.showError(exception.getMessage());
	}

	@Override
	public void onHeartbeat() {
	}

	@Override
	public void onRefresh() {
	}

	@Override
	public void onMessage(List<?> messages) {
		for (Object message : messages) {
			onMessage((CometMessage) message);
		}
	}

	private synchronized static void onMessage(CometMessage message) {
		if (message instanceof CometApiMessage) {
			if (apiListener != null) {
				apiListener.receiveResponse(message);
			}
		} else if (message instanceof CometErrorResponse) {
			if (errorListener != null) {
				errorListener.receiveResponse(message);
			}
		} else if (responseListener != null) {
			logger.info("Comet client receive:" + message);
			responseListener.receiveResponse(message);
		}
	}

}
