package pl.eHouse.web.common.client.comet;

import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.SerialTypes;

import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootFindMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootFindResponse;
import pl.eHouse.web.common.client.comet.messages.CometBootModeMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootStartMessage;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;
import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadFinish;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetResponse;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.comet.messages.CometEepromSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryMessage;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryResponse;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressMessage;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressResponse;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreResponse;
import pl.eHouse.web.common.client.comet.messages.CometRestartMessage;
import pl.eHouse.web.common.client.comet.messages.CometSendMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareDeleteMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveResponse;

@SerialTypes(value = { CometErrorResponse.class, CometSendMessage.class,
		CometDeviceLoadFinish.class, CometNetworkExploreMessage.class,
		CometNetworkExploreResponse.class, CometApiMessage.class,
		CometFreeAddressMessage.class, CometFreeAddressResponse.class,
		CometNetworkAllocateMessage.class, CometNetworkAllocateResponse.class,
		CometHardwareLoadMessage.class, CometHardwareLoadResponse.class,
		CometDeviceLoadMessage.class, CometDeviceLoadResponse.class,
		CometDeviceSetMessage.class, CometDeviceSetResponse.class,
		CometBootModeMessage.class, CometSoftwareLoadMessage.class,
		CometSoftwareLoadResponse.class, CometSoftwareDeleteMessage.class,
		CometBootFindMessage.class, CometBootFindResponse.class,
		CometSoftwareSaveMessage.class, CometSoftwareSaveResponse.class,
		CometBootStartMessage.class, CometEepromStatusMessage.class,
		CometEepromStatusResponse.class, CometEepromGetMessage.class,
		CometEepromGetResponse.class, CometEepromSetMessage.class,
		CometCommandMessage.class, CometCommandResponse.class,
		CometRestartMessage.class, CometFindHistoryMessage.class,
		CometFindHistoryResponse.class })
public abstract class CometSerializer extends AtmosphereGWTSerializer {
}
