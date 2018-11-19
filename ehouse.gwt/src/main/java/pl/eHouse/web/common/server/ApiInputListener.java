package pl.eHouse.web.common.server;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.listeners.IInputListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.message.MessageInBoot;
import pl.eHouse.api.message.MessageInSerial;
import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.server.utils.GwtOwner;
import pl.eHouse.web.common.server.utils.Timestamp;

public class ApiInputListener implements IInputListener {
	
	private GwtAtmosphereResource resource;

	public ApiInputListener(GwtAtmosphereResource resource) {
		super();
		this.resource = resource;
	}

	@Override
	public void messageIn(MessageInAddress mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}
	
	@Override
	public void messageIn(MessageInSerial mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}
	
	@Override
	public void messageIn(MessageInBoot mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}

	@Override
	public String getId() {		
		return GwtOwner.get(resource);
	}

}
