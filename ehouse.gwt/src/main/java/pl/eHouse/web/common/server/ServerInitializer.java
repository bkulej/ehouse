package pl.eHouse.web.common.server;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.config.Settings;
import pl.eHouse.api.config.SettingsException;
import pl.eHouse.api.listeners.ListenerException;

public class ServerInitializer {

	public ServerInitializer() {
		super();
	}

	public void init(Communicator comm) throws SettingsException,
			ConfigException, ListenerException {
		Settings.init();
		Config.getInstance();
		comm.connect(Settings.getSocketAddress(), Settings.getSocketPort());
	}

}
