package pl.eHouse.web.common.client.widgets.large;

import pl.eHouse.web.common.client.command.IReceiveMapper;
import pl.eHouse.web.common.client.command.ISendMapper;
import pl.eHouse.web.common.client.command.ReceiveMapper;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.widgets.IWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetSwitchLarge extends Button implements IWidget,
		IReceiveMapper, ISendMapper {

	public final static String STATUS_OFF = "off";
	public final static String STATUS_ON = "on";
	public final static String STATUS_WARN = "warn";
	public final static String STATUS_UNDEFINED = "undef";

	public final static String VALUE_PLACE = "#";

	private SendMapper sender;
	private PopupInputLarge popup;

	private int height = 150;
	private int width = 150;
	private String status;
	private String patern;
	private String value;

	public WidgetSwitchLarge(String patern) {
		super(patern);
		this.patern = patern;
		setPixelSize(width - 5, height - 5);
		setStatus(STATUS_UNDEFINED);
	}

	public WidgetSwitchLarge(String name, ClickHandler handler) {
		super(name, handler);
		setPixelSize(width - 5, height - 5);
		setStatus(STATUS_UNDEFINED);
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		setPixelSize(width - 5, height - 5);
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		setPixelSize(width - 5, height - 5);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		switch (status) {
		case STATUS_OFF:
			setStylePrimaryName("gwt-SwitchLarge-off");
			break;
		case STATUS_ON:
			setStylePrimaryName("gwt-SwitchLarge-on");
			break;
		case STATUS_WARN:
			setStylePrimaryName("gwt-SwitchLarge-warn");
			break;
		default:
			setStylePrimaryName("gwt-SwitchLarge-undef");
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setHTML(patern.replaceAll(VALUE_PLACE, value));
	}

	@Override
	public void setSender(SendMapper sender1) {
		this.sender = sender1;
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sender.sendMapping(status);
			}
		});
	}
	
	public void setPopupInputLarge(PopupInputLarge popup1) {
		this.popup = popup1;
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popup.show(value);
			}
		});
	}

	@Override
	public SendMapper getSender() {
		return sender;
	}

	@Override
	public void addReceiver(ReceiveMapper receiver) {
		receiver.setWidget(this);
	}

	@Override
	public void receiveStatus(String status) {
		setStatus(status);
	}

	@Override
	public void receiveValue(String value) {
		setValue(value);
	}

	@Override
	public void sendInitial() {
		sender.sendMapping(STATUS_UNDEFINED);
	}

}
