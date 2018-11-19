package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometSoftwareSaveResponse extends CometMessage {
	
	private static final long serialVersionUID = 3414317385576762282L;
		
	public final static int STATUS_WORKING		= 0;
	public final static int STATUS_START		= 1;
	public final static int STATUS_END_OK 		= 2;
	public final static int STATUS_END_ERROR 	= 3;
	
	private String hardSerial;
	private String softId;
	private String softType;
	private int progres;
	private int status;

	public CometSoftwareSaveResponse() {
		super();
	}

	public CometSoftwareSaveResponse(String hardSerial, String softId,
			String softType, int progres, int status) {
		super();
		this.hardSerial = hardSerial;
		this.softId = softId;
		this.softType = softType;
		this.progres = progres;
		this.status = status;
	}

	public String getHardSerial() {
		return hardSerial;
	}

	public void setHardSerial(String hardSerial) {
		this.hardSerial = hardSerial;
	}

	public String getSoftId() {
		return softId;
	}

	public void setSoftId(String softId) {
		this.softId = softId;
	}

	public String getSoftType() {
		return softType;
	}

	public void setSoftType(String softType) {
		this.softType = softType;
	}

	public int getProgres() {
		return progres;
	}

	public void setProgres(int progres) {
		this.progres = progres;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CometSoftwareSaveResponse [hardSerial=" + hardSerial
				+ ", softId=" + softId + ", softType=" + softType
				+ ", progres=" + progres + ", status=" + status + "]";
	}

	
}
