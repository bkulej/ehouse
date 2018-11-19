package pl.eHouse.web.common.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class WidgetPanel extends LayoutPanel implements IWidget {
	
		private int height;
		private int width;		

	public WidgetPanel(int width, int height) {		
		this.height = height;
		this.width = width;
		setPixelSize(width,height);
		setStylePrimaryName("app-panel");
	}
	
	public WidgetPanel(int width, int height, String style) {		
		this.height = height;
		this.width = width;
		setPixelSize(width,height);
		setStylePrimaryName(style);
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public void addWidget(int x, int y, IWidget widget) {
		add(widget);		
		setWidgetLeftWidth(widget, x, Unit.PX, widget.getWidth(), Unit.PX);
		setWidgetTopHeight(widget, y, Unit.PX, widget.getHeight(), Unit.PX);
	}
		
	public void addWidget(int x, int y, int width, int height, Widget widget) {
		add(widget);		
		setWidgetLeftWidth(widget, x, Unit.PX, width, Unit.PX);
		setWidgetTopHeight(widget, y, Unit.PX, height, Unit.PX);
	}

}
