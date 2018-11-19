package pl.eHouse.web.common.client.comet;

import java.util.logging.Logger;

import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.impl.WebSocketCometTransport;

import com.google.gwt.core.client.GWT;

public class CometSender {

	private static Logger logger = Logger.getLogger("CometSender");
	private static AtmosphereClient client;

	public static void init() {
		if (client == null) {
			CometListener listener = new CometListener();
			boolean websocket = WebSocketCometTransport.hasWebSocketSupport();
			AtmosphereGWTSerializer serializer = GWT
					.create(CometSerializer.class);
			client = new AtmosphereClient(GWT.getModuleBaseURL().replaceAll(
					GWT.getModuleName(), "common")
					+ "comet", serializer, listener, websocket);
			client.setConnectionTimeout(40000);
			client.start();
		}
	}

	public static void send(CometMessage message, CometInterface listener) {
		init();
		logger.info("Comet client send messages:" + message);
		CometListener.setResponseListener(listener);
		client.post(message);
	}
	
	public static void send(CometMessage message) {
		init();
		logger.info("Comet client send messages:" + message);
		client.post(message);
	}

}
