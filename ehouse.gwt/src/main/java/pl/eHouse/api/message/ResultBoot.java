/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.message;

/**
 * 
 * @author Bartek
 */
public class ResultBoot extends Result {

	protected MessageInBoot messIn;
	protected MessageOutBoot messOut;

	public ResultBoot(MessageOutBoot messOut) {
		this.messOut = messOut;
	}

	public ResultBoot(MessageOutBoot messOut, MessageInBoot messIn) {
		this.messIn = messIn;
		this.messOut = messOut;
	}

	public MessageInBoot getMessIn() {
		return messIn;
	}

	public MessageOutBoot getMessOut() {
		return messOut;
	}

	public int getStatus() {
		if (messIn != null) {
			return STATUS_RESPONSE;
		}
		if (messOut.isBroadcast()) {
			return STATUS_FINISHED;
		}
		return STATUS_NO_REPLY;
	}

	@Override
	public String toString() {
		return "<RB>{serial=" + messOut.getSerial() + ",id=" + messOut.getId()
				+ ",status=" + getStatusText() + "}";
	}

	private String getStatusText() {
		if (messIn != null) {
			return "RESPONSE";
		}
		if (messOut.isBroadcast()) {
			return "FINISHED";
		}
		return "NO REPLY";
	}

	/**
	 * Zwrocenie seriala
	 * 
	 * @return
	 */
	public String getSerial() {
		if (messIn != null) {
			return messIn.getSerial();
		} else {
			return messOut.getSerial();
		}
	}

	/**
	 * Typ hardware
	 * 
	 * @return
	 */
	public Integer getHardwareType() {
		if ((messIn != null) && (messIn.getData() != null)
				&& (messIn.getData().length() == 13)) {
			return messIn.getDataWord(0);
		} else {
			return null;
		}
	}

	/**
	 * Rozmiar pamieci
	 * 
	 * @return
	 */
	public Integer getMemorySize() {
		if ((messIn != null) && (messIn.getData() != null)
				&& (messIn.getData().length() == 13)) {
			return messIn.getDataWord(4);
		} else {
			return null;
		}
	}

	/**
	 * Rozmiar strony
	 * 
	 * @return
	 */
	public Integer getPageSize() {
		if ((messIn != null) && (messIn.getData() != null)
				&& (messIn.getData().length() == 13)) {
			return messIn.getDataWord(8);
		} else {
			return null;
		}
	}

	/**
	 * Status sprzetu
	 * 
	 * @return
	 */
	public String getHardwareStatus() {
		if ((messIn != null) && (messIn.getData() != null)
				&& (messIn.getData().length() == 13)) {
			return messIn.getDataChar(12) + "";
		} else {
			return null;
		}
	}

}
