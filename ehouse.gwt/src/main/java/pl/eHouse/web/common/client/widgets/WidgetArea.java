package pl.eHouse.web.common.client.widgets;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextArea;

public class WidgetArea extends TextArea implements IWidget{
	
	private int height; 	
	private int width;

	public WidgetArea(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setPixelSize(width-4,height-4);		
	}	
	
	public WidgetArea(int width, int height, ChangeHandler handler) {
		this(width,height);		
		addChangeHandler(handler);
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
