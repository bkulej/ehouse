package pl.eHouse.web.common.client.widgets.large;

import pl.eHouse.web.common.client.command.IReceiveMapper;
import pl.eHouse.web.common.client.command.ReceiveMapper;
import pl.eHouse.web.common.client.widgets.IWidget;

import com.google.gwt.user.client.ui.Label;

public class WidgetTextLarge extends Label implements IWidget, IReceiveMapper{
	
	private static int sHeight = 50;
	private int height; 	
	private int width;
	
	public WidgetTextLarge(int width, String text, HorizontalAlignmentConstant align) {
		this(width, sHeight,text,align);
	}
		
	public WidgetTextLarge(int width, int height, String text, HorizontalAlignmentConstant align) {
		super(text);
		this.height = height;
		this.width = width;
		setStylePrimaryName("gwt-LabelLarge");
		setPixelSize(width,height - 7);
		setHorizontalAlignment(align);
	}	
	
	@Override
	public int getHeight() {	
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void addReceiver(ReceiveMapper receiver) {
		receiver.setWidget(this);
	}
	
	@Override
	public void receiveStatus(String status) {
	}
	
	@Override
	public void receiveValue(String value) {
		setText(value.replace(';', ' '));
	}
	
}
