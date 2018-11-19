package pl.eHouse.web.common.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetButton extends Button implements IWidget {
	
	private int height = 22;
	private int width = 80;
	
	public WidgetButton(String name) {
		super(name);
		setPixelSize( width - 2,height - 2);
	}
	
	public WidgetButton(String name, ClickHandler handler) {
		super(name, handler);				
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
