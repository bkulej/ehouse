package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometFreeAddressResponse extends CometMessage {

	private static final long serialVersionUID = -3610248728446031257L;
	
	private String addresses = "";

	public CometFreeAddressResponse() {
		super();
	}

	public String[] getAddresses() {
		return addresses.split(";");
	}

	public void addAddress(String addresses) {
		this.addresses += addresses + ";";
	}

	@Override
	public String toString() {
		return "CometFreeAddressResponse [addresses=" + addresses + "]";
	}
	
}
