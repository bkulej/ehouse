package pl.eHouse.web.common.server;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.listeners.IResultlListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.Command;
import pl.eHouse.api.message.MessageBoot;
import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.server.execute.BootFindExecutor;
import pl.eHouse.web.common.server.execute.EepromGetExecutor;
import pl.eHouse.web.common.server.execute.EepromSetExecutor;
import pl.eHouse.web.common.server.execute.EepromStatusExecutor;
import pl.eHouse.web.common.server.execute.NetworkAllocateExecutor;
import pl.eHouse.web.common.server.execute.NetworkExploreExecutor;
import pl.eHouse.web.common.server.execute.CommandExecutor;
import pl.eHouse.web.common.server.utils.GwtOwner;
import pl.eHouse.web.common.server.utils.Timestamp;

public class ApiResultListener implements IResultlListener {

	private GwtAtmosphereResource resource;

	public ApiResultListener(GwtAtmosphereResource resource) {
		super();
		this.resource = resource;
	}

	@Override
	public void result(ResultAddress result) throws ListenerException {
		try {
			if (GwtOwner.get(resource).equals(result.getMessOut().getOwner())) {
				if (result.getStatus() == ResultAddress.STATUS_RESPONSE) {
					// COMMAND
					CommandExecutor.response(result, resource);
				} else if ((result.getStatus() == ResultAddress.STATUS_NO_REPLY)
						|| (result.getStatus() == ResultAddress.STATUS_FINISHED)) {
					// BRAK ODPOWIEDZI
					resource.post(new CometApiMessage(result.toString(),
							Timestamp.getTextTime()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage());
		}
	}

	@Override
	public void result(ResultSerial result) throws ListenerException {
		try {
			if (GwtOwner.get(resource).equals(result.getMessOut().getOwner())) {
				// Jestem wlascicielem request'a
				if ((result.getStatus() == ResultSerial.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(Command.REQUEST_EEPROM_STATUS)) {
					EepromStatusExecutor.response(result, resource);
					// EEPROM SET
				} else if ((result.getStatus() == ResultSerial.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(Command.REQUEST_EEPROM_SET)) {
					EepromSetExecutor.response(result, resource);
					// EEPROM GET
				} else if ((result.getStatus() == ResultSerial.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(Command.REQUEST_EEPROM_GET)) {
					EepromGetExecutor.response(result, resource);
				} else if ((result.getStatus() == ResultSerial.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(Command.REQUEST_NETWORK_FIND)) {
					// Odpowiedü Network.EXPLORE
					NetworkExploreExecutor.response((ResultSerial) result,
							resource);
				} else if ((result.getStatus() == ResultSerial.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(Command.REQUEST_NETWORK_ALLOCATE)) {
					// Odpowiedü Network.ALOCATE
					NetworkAllocateExecutor.response((ResultSerial) result,
							resource);
				} else if ((result.getStatus() == ResultSerial.STATUS_NO_REPLY)
						|| (result.getStatus() == ResultSerial.STATUS_FINISHED)) {
					// Brak opowiedzi
					resource.post(new CometApiMessage(result.toString(),
							Timestamp.getTextTime()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage());
		}
	}

	@Override
	public void result(ResultBoot result) throws ListenerException {
		try {
			// Czy moj request
			if (GwtOwner.get(resource).equals(result.getMessOut().getOwner())) {
				// Odpowiedzi Boot.FIND
				if ((result.getStatus() == ResultBoot.STATUS_RESPONSE)
						&& result.getMessOut().getCommand()
								.equals(MessageBoot.FIND_REQUEST)) {
					BootFindExecutor.response((ResultBoot) result, resource);
					// Brak opowiedzi
				} else if ((result.getStatus() == ResultBoot.STATUS_NO_REPLY)
						|| (result.getStatus() == ResultBoot.STATUS_FINISHED)) {
					resource.post(new CometApiMessage(result.toString(),
							Timestamp.getTextTime()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage());
		}
	}

	@Override
	public String getId() {
		return GwtOwner.get(resource);
	}

}
