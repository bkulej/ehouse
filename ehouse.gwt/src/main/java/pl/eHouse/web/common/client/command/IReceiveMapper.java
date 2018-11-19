package pl.eHouse.web.common.client.command;


public interface IReceiveMapper {
	
	public void receiveStatus(String status);
	public void receiveValue(String value);
	public void addReceiver(ReceiveMapper receiver);

}
