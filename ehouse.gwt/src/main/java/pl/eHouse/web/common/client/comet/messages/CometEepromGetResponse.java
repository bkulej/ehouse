package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.utils.SpliterUtils;

public class CometEepromGetResponse extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String page;
	private String device;
	private String type;
	private String values;

	public CometEepromGetResponse() {
		super();
	}

	public CometEepromGetResponse(String serial, String page, String device, String type) {
		super();
		this.serial = serial;
		this.page = page;
		this.device = device;
		this.type = type;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getValue(String key) {
		return SpliterUtils.valueSplit(values, key);
	}

	@Override
	public String toString() {
		return "CometEepromGetResponse [serial=" + serial + ", page=" + page
				+ ", device=" + device + ", type=" + type + ", values="
				+ values + "]";
	}

}
