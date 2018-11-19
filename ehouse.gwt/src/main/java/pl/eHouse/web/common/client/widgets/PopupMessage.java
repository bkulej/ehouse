package pl.eHouse.web.common.client.widgets;

import pl.eHouse.web.common.client.locale.LocaleFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupMessage extends PopupPanel {

	private static PopupMessage dialog = new PopupMessage();

	private WidgetPanel panel;
	private WidgetText wtText1;
	private WidgetText wtText2;
	private WidgetButton wbYes;
	private WidgetButton wbNo;
	private WidgetButton wbOk;
	private HandlerRegistration oldHandler;

	private PopupMessage() {
		// Inicjacja okna
		super();
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setPixelSize(530, 75);
		// Panel
		panel = new WidgetPanel(530, 75);
		// Text1
		wtText1 = new WidgetText(510, "", WidgetText.ALIGN_CENTER);
		panel.addWidget(10, 10, wtText1);
		// Label serial
		wtText2 = new WidgetText(510, "", WidgetText.ALIGN_CENTER);
		panel.addWidget(10, 40, wtText2);
		// Button Yes
		wbYes = new WidgetButton(LocaleFactory.getLabels()
				.MessageDialog_butYes());
		wbYes.setWidth(50);
		panel.addWidget(210, 40, wbYes);
		// Button No
		wbNo = new WidgetButton(
				LocaleFactory.getLabels().MessageDialog_butNo(),
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						hide();
					}
				});
		wbNo.setWidth(50);
		panel.addWidget(270, 40, wbNo);
		// Button OK
		wbOk = new WidgetButton(
				LocaleFactory.getLabels().MessageDialog_butOk(),
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						hide();
					}
				});
		wbOk.setWidth(50);
		panel.addWidget(470, 40, wbOk);
		// Inicjacja
		setWidget(panel);
		hide();
	}

	private void show(ClickHandler newHandler) {
		if (oldHandler != null) {
			oldHandler.removeHandler();
		}
		if (newHandler != null) {
			oldHandler = wbYes.addClickHandler(newHandler);
		}
		center();
		show();
	}

	public static void close() {
		dialog.hide();
	}

	public static void showInfo(String message) {
		dialog.wbYes.setVisible(false);
		dialog.wbNo.setVisible(false);
		dialog.wbOk.setVisible(true);
		dialog.wtText1.setVisible(true);
		dialog.wtText1.setText(message);
		dialog.wtText2.setVisible(false);
		dialog.show(null);
	}

	public static void showWarning(String message) {
		dialog.wbYes.setVisible(false);
		dialog.wbNo.setVisible(false);
		dialog.wbOk.setVisible(true);
		dialog.wtText1.setVisible(true);
		dialog.wtText1.setText(message);
		dialog.wtText2.setVisible(false);
		dialog.show(null);
	}

	public static void showError(String message) {
		dialog.wbYes.setVisible(false);
		dialog.wbNo.setVisible(false);
		dialog.wbOk.setVisible(true);
		dialog.wtText1.setVisible(true);
		dialog.wtText1.setText(message);
		dialog.wtText2.setVisible(false);
		dialog.show(null);
	}

	public static void showQuestion(String message, ClickHandler handler) {
		dialog.wbYes.setVisible(true);
		dialog.wbNo.setVisible(true);
		dialog.wbOk.setVisible(false);
		dialog.wtText1.setVisible(true);
		dialog.wtText1.setText(message);
		dialog.wtText2.setVisible(false);
		dialog.show(handler);
	}

	public static void showWait(String message) {
		dialog.wbYes.setVisible(false);
		dialog.wbNo.setVisible(false);
		dialog.wbOk.setVisible(false);
		dialog.wtText1.setVisible(true);
		dialog.wtText1.setText(message);
		dialog.wtText2.setVisible(true);
		dialog.wtText2.setText(LocaleFactory.getLabels()
				.MessageDialog_texWait());
		dialog.show(null);
	}

}
