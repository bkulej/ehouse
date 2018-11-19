package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultException;
import pl.eHouse.api.message.ResultSerial;

public class ResultEepromStatus {
	
	private ResultSerial result;
	private int start;
	private int count;

	public ResultEepromStatus(ResultSerial result) throws ResultException {
		super();
		if(result == null) {
			throw new ResultException("Result is null");
		}
		if(result.getMessIn() == null) {
			throw new ResultException("Message IN is null");
		}
		if(result.getMessIn().getData().length() < 2) {
			throw new ResultException("Bad data");
		}
		this.result = result;
		this.start = result.getMessIn().getDataByteAsInt(0);	
		this.count = (result.getMessIn().getData().length()/2)-1;
	}
	
	public int getPagesCount() {
		return count;
	}
	
	public int getPage(int pos) {
		return pos < count ? start + pos : 0xFF;
	}

	
	public int getDevice( int pos) {
		return pos < count ? result.getMessIn().getDataByteAsInt((pos + 1)*2) : 0xFF;
	}

}
