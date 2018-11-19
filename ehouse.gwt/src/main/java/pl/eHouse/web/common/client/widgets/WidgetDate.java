package pl.eHouse.web.common.client.widgets;

import java.util.Date;

import pl.eHouse.web.common.client.utils.ClientConvert;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class WidgetDate extends Button implements IWidget {
	
	private static int height = 22;
	private int width = 100;
	private Date date;
	
	public WidgetDate() {
		super();
		setPixelSize( width - 2,height - 2);
		setDate(new Date());
	}
	
	public WidgetDate(ClickHandler handler) {
		super();		
		addClickHandler(handler);
		setPixelSize( width - 2,height - 2);
		setDate(new Date());
	}		
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public void setDate(Date date) {
		this.date = date;
		setText(ClientConvert.dateToString(date));
	}

	public Date getDate() {
		return date;
	}
	
	public String getTextDate() {
		return getText();
	}
	
}
