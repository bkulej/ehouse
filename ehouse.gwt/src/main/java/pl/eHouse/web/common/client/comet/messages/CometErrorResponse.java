package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometErrorResponse extends CometMessage {

	private static final long serialVersionUID = 666929807134325278L;
	
	private String message;

	public CometErrorResponse() {
		super();
	}

	public CometErrorResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CometError [message=" + message + "]";
	}

}
