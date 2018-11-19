package pl.eHouse.web.common.server;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.listeners.IOutputListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.MessageOutSerial;
import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.server.utils.GwtOwner;
import pl.eHouse.web.common.server.utils.Timestamp;

public class ApiOutputListener implements IOutputListener {
	
	private GwtAtmosphereResource resource;

	public ApiOutputListener(GwtAtmosphereResource resource) {
		super();
		this.resource = resource;
	}

	@Override
	public void messageOut(MessageOutSerial mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}
	
	@Override
	public void messageOut(MessageOutAddress mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}
	
	@Override
	public void messageOut(MessageOutBoot mess) throws ListenerException {
		resource.post(new CometApiMessage(mess.toString(), Timestamp.getTextTime()));
	}

	@Override
	public String getId() {
		return GwtOwner.get(resource);
	}

}
