package pl.eHouse.web.common.client.widgets.large;

import pl.eHouse.web.common.client.widgets.IWidget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetIconLarge extends Button implements IWidget {
	
	private static int height = 230;
	private int width = 230;
	
	public WidgetIconLarge(String name) {
		super(name);
		setPixelSize( width - 2,height - 2);
		setStylePrimaryName("gwt-IconLarge");
	}
	
	public WidgetIconLarge(String name, ClickHandler handler) {
		super(name, handler);				
		setPixelSize( width - 2,height - 2);
		setStylePrimaryName("gwt-IconLarge");
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
