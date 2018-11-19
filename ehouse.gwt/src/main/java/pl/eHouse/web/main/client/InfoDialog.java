package pl.eHouse.web.main.client;

import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.IWidget;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetText;
import pl.eHouse.web.common.client.widgets.large.WidgetButtonLarge;
import pl.eHouse.web.common.client.widgets.large.WidgetTextLarge;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public abstract class InfoDialog extends WidgetDialog {

	private WidgetTextLarge wtTitle;
	private WidgetButtonLarge wbClose;

	public InfoDialog(int width, int height, String title) {
		super(width, height + 50);
		// Label serial
		wtTitle = new WidgetTextLarge(width - 50, title,
				WidgetText.ALIGN_CENTER);
		super.addWidget(0, 0 + 2, wtTitle);
		// Button Cancel
		wbClose = new WidgetButtonLarge(LocaleFactory.getLabels()
				.InfoDialog_butClose(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideDialog();
				hide();
			}
		});
		wbClose.setWidth(50);
		super.addWidget(width - 50, 0 + 2, wbClose);
		hide();
	}

	@Override
	public void addWidget(int x, int y, IWidget widget) {
		super.addWidget(x, y + 50, widget);
	}

	protected abstract void showDialog();

	protected abstract void hideDialog();

}
