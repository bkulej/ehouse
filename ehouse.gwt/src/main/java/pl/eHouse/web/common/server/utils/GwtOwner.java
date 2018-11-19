package pl.eHouse.web.common.server.utils;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

public class GwtOwner {

	public static String get(GwtAtmosphereResource resource) {
		return "GWT-" + resource.getConnectionUUID();
	}

}
