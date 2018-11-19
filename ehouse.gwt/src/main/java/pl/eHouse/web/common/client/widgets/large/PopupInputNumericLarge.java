package pl.eHouse.web.common.client.widgets.large;

import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.WidgetPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupInputNumericLarge extends PopupPanel implements
		PopupInputLarge {

	public final static String VALUE_PLACE = "#";

	private WidgetPanel panel;
	private WidgetButtonLarge wbUp;
	private WidgetButtonLarge wbValue;
	private WidgetButtonLarge wbDown;
	private String device;
	private String command;
	private String template;
	private Integer value;

	public PopupInputNumericLarge(String template, String device, String command) {
		// Inicjacja okna
		super();
		this.template = template;
		this.device = device;
		this.command = command;
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setPixelSize(170, 330);
		// Panel
		panel = new WidgetPanel(170, 330);
		// Button Up
		wbUp = new WidgetButtonLarge(LocaleFactory.getLabels()
				.PopupInputNumericLarge_butUp(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setValue(++value);
			}
		});
		wbUp.setHeight(70);
		panel.addWidget(10, 10, wbUp);
		// Button Value
		wbValue = new WidgetButtonLarge("#", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				close();
			}
		});
		wbValue.setHeight(150);
		panel.addWidget(10, 90, wbValue);
		// Button Up
		wbDown = new WidgetButtonLarge(LocaleFactory.getLabels()
				.PopupInputNumericLarge_butDown(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setValue(--value);
			}
		});
		wbDown.setHeight(70);
		panel.addWidget(10, 250, wbDown);
		// Inicjacja
		setWidget(panel);
		hide();
	}

	private void setValue(Integer value) {
		this.value = value;
		wbValue.setHTML(template.replaceAll(VALUE_PLACE, value.toString()));
	}

	private void send() {
		CometSender.send(new CometCommandMessage(device, command.replaceAll(
				VALUE_PLACE, value.toString())));
	}

	@Override
	public void show(String value) {
		try {
			setValue(Float.valueOf(value).intValue());
		} catch (Exception e) {
			return;
		}
		center();
		show();
	}

	public void close() {
		send();
		hide();
	}

}
