package pl.eHouse.web.common.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.web.common.client.comet.messages.CometBootFindMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootModeMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootStartMessage;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusMessage;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryMessage;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressMessage;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreMessage;
import pl.eHouse.web.common.client.comet.messages.CometRestartMessage;
import pl.eHouse.web.common.client.comet.messages.CometSendMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareDeleteMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveMessage;
import pl.eHouse.web.common.server.execute.BootFindExecutor;
import pl.eHouse.web.common.server.execute.BootModeExecutor;
import pl.eHouse.web.common.server.execute.BootStartExecutor;
import pl.eHouse.web.common.server.execute.CommandExecutor;
import pl.eHouse.web.common.server.execute.DeviceLoadExecutor;
import pl.eHouse.web.common.server.execute.DeviceSetExecutor;
import pl.eHouse.web.common.server.execute.EepromGetExecutor;
import pl.eHouse.web.common.server.execute.EepromSetExecutor;
import pl.eHouse.web.common.server.execute.EepromStatusExecutor;
import pl.eHouse.web.common.server.execute.FreeAddressExecutor;
import pl.eHouse.web.common.server.execute.HardwareLoadExecutor;
import pl.eHouse.web.common.server.execute.HistoryExecutor;
import pl.eHouse.web.common.server.execute.NetworkAllocateExecutor;
import pl.eHouse.web.common.server.execute.NetworkExploreExecutor;
import pl.eHouse.web.common.server.execute.RestartExecutor;
import pl.eHouse.web.common.server.execute.SendMessageExecutor;
import pl.eHouse.web.common.server.execute.SoftwareDeleteExecutor;
import pl.eHouse.web.common.server.execute.SoftwareLoadExecutor;
import pl.eHouse.web.common.server.execute.SoftwareSaveExecutor;

public class WebInputListener {

	public static void messageIn(Object message, GwtAtmosphereResource resource) {
		Logger.getLogger(WebInputListener.class.getName()).log(
				Level.INFO, message.toString());
		if (message instanceof CometSendMessage) { 
			SendMessageExecutor.execute((CometSendMessage) message,resource);
		} else if (message instanceof CometNetworkExploreMessage) {
			NetworkExploreExecutor.execute((CometNetworkExploreMessage) message,resource);
		} else if (message instanceof CometBootModeMessage) {
			BootModeExecutor.execute((CometBootModeMessage) message,resource);
		} else if (message instanceof CometRestartMessage) {
			RestartExecutor.execute((CometRestartMessage) message,resource);
		} else if (message instanceof CometFreeAddressMessage) {
			FreeAddressExecutor.execute((CometFreeAddressMessage)message, resource);
		} else if (message instanceof CometNetworkAllocateMessage) {
			NetworkAllocateExecutor.execute((CometNetworkAllocateMessage)message, resource);
		} else if (message instanceof CometHardwareLoadMessage) {
			HardwareLoadExecutor.execute((CometHardwareLoadMessage)message, resource);
		} else if (message instanceof CometDeviceLoadMessage) {
			DeviceLoadExecutor.execute((CometDeviceLoadMessage)message, resource);
		} else if (message instanceof CometDeviceSetMessage) {
			DeviceSetExecutor.execute((CometDeviceSetMessage)message, resource);
		} else if (message instanceof CometSoftwareLoadMessage) {
			SoftwareLoadExecutor.execute((CometSoftwareLoadMessage)message, resource);
		} else if(message instanceof CometSoftwareDeleteMessage) {
			SoftwareDeleteExecutor.execute((CometSoftwareDeleteMessage)message, resource);
		} else if(message instanceof CometBootFindMessage) {
			BootFindExecutor.execute((CometBootFindMessage)message, resource);
		} else if(message instanceof CometSoftwareSaveMessage) {
			SoftwareSaveExecutor.execute((CometSoftwareSaveMessage)message, resource);
		} else if(message instanceof CometBootStartMessage) {
			BootStartExecutor.execute((CometBootStartMessage)message, resource);
		} else if(message instanceof CometEepromStatusMessage ) {
			EepromStatusExecutor.execute((CometEepromStatusMessage)message, resource);
		} else if(message instanceof CometEepromSetMessage ) {
			EepromSetExecutor.execute((CometEepromSetMessage)message, resource);
		}  else if(message instanceof CometEepromGetMessage ) {
			EepromGetExecutor.execute((CometEepromGetMessage)message, resource);
		} else if(message instanceof CometCommandMessage) {
			CommandExecutor.execute((CometCommandMessage)message, resource);
		} else if(message instanceof CometFindHistoryMessage) {
			HistoryExecutor.execute((CometFindHistoryMessage)message, resource);
		}
	}
}
