package pl.eHouse.web.common.client.widgets;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

public class WidgetInput extends TextBox implements IWidget {
	
	private int height = 22; 	
	private int width;
		
	public WidgetInput(int width) {
		super();
		this.width = width;
		setPixelSize(width - 7 ,height - 7);
	}
	
	public WidgetInput(int width, int length) {
		super();
		this.width = width;
		setPixelSize(width - 7,height - 7);
		setMaxLength(length);		
	}
	
	public WidgetInput(int width, ChangeHandler handler) {
		this(width);
		addChangeHandler(handler);		
	}
	
	public WidgetInput(int width, int length, ChangeHandler handler) {
		this(width,length);
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
