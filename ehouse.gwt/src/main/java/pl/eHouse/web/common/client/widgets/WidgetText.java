package pl.eHouse.web.common.client.widgets;

import com.google.gwt.user.client.ui.Label;

public class WidgetText extends Label implements IWidget {
	
	private static int sHeight = 22;
	private int height; 	
	private int width;
		
	public WidgetText(int width, String text, HorizontalAlignmentConstant align) {
		this(width, sHeight,text,align);
	}	
	
	public WidgetText(int width, int height, String text, HorizontalAlignmentConstant align) {
		super(text);
		this.height = height;
		this.width = width;
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

}
