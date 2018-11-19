package pl.eHouse.web.common.client.widgets;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class WidgetDialog extends PopupPanel {
	
	private WidgetPanel panel;

	public WidgetDialog(int width, int height) {
		super();
		// Inicjacja okna
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setPixelSize(width, height);
		// Panel
		panel = new WidgetPanel(width, height);
		setWidget(panel);
	}
	
	public void addWidget(int x, int y, IWidget widget) {
		panel.addWidget(x, y, widget);
	}
	
	public void addWidget(int x, int y, int width, int height, Widget widget) {
		panel.addWidget(x, y, width, height, widget);
	}
	
	public void addWidget(Widget widget) {
		panel.add(widget);
	}
	
	public WidgetPanel getPanel() {
		return panel;
	}

}
