package pl.eHouse.web.common.client.command;

public interface ISendMapper {
	
	public void setSender(SendMapper sender);
	public SendMapper getSender();
	public void sendInitial();

}
