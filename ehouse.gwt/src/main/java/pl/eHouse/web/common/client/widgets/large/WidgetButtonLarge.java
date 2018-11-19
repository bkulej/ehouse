package pl.eHouse.web.common.client.widgets.large;

import pl.eHouse.web.common.client.widgets.IWidget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetButtonLarge extends Button implements IWidget {
	
	private int height = 50;
	private int width = 150;
	
	public WidgetButtonLarge(String name) {
		super(name);
		setStylePrimaryName("gwt-ButtonLarge");
		setPixelSize( width - 2,height - 2);
	}
	
	public WidgetButtonLarge(String name, ClickHandler handler) {
		super(name, handler);				
		setStylePrimaryName("gwt-ButtonLarge");
		setPixelSize( width - 2,height - 2);
	}		
	
	@Override
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
		setPixelSize( width - 2,height - 2);
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		setPixelSize( width - 2,height - 2);
	}
	
}
