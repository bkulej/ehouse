package pl.eHouse.web.common.client.widgets;

import com.google.gwt.user.client.ui.FileUpload;

public class WidgetUpload extends FileUpload implements IWidget {
	
	private static int height = 22;
	private int width = 200; 
	
	public WidgetUpload(String name) {
		super();
		setName(name);
		setPixelSize( width - 2,height);
	}		
	
	@Override
	public int getHeight() {
		return height;
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
