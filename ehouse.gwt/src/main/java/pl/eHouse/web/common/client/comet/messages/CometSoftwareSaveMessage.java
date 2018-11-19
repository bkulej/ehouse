package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometSoftwareSaveMessage extends CometMessage {

	private static final long serialVersionUID = 3414317385576762282L;

	private String hardSerial;
	private String memorySize;
	private String pageSize;
	private String softId;
	private String softType;

	public CometSoftwareSaveMessage() {
		super();
	}

	public CometSoftwareSaveMessage(String hardSerial, String memorySize,
			String pageSize, String softId, String softType) {
		super();
		this.hardSerial = hardSerial;
		this.memorySize = memorySize;
		this.pageSize = pageSize;
		this.softId = softId;
		this.softType = softType;
	}

	public String getHardSerial() {
		return hardSerial;
	}

	public void setHardSerial(String hardSerial) {
		this.hardSerial = hardSerial;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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
	
	public String getFileName() {
		return softId + "." + softType;
	}

	@Override
	public String toString() {
		return "CometSoftwareSaveMessage [hardSerial=" + hardSerial
				+ ", memorySize=" + memorySize + ", pageSize=" + pageSize
				+ ", softId=" + softId + ", softType=" + softType + "]";
	}

}
