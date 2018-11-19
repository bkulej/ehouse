package pl.eHouse.web.common.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetIcon extends Button implements IWidget {
	
	private static int height = 100;
	private int width = 100;
	
	public WidgetIcon(String name) {
		super(name);
		setPixelSize( width - 2,height - 2);
		setStylePrimaryName("gwt-Icon");
	}
	
	public WidgetIcon(String name, ClickHandler handler) {
		super(name, handler);				
		setPixelSize( width - 2,height - 2);
		setStylePrimaryName("gwt-Icon");
	}		
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
}
