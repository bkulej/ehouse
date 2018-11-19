package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetMessage;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.WidgetArea;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class DeviceDialog extends WidgetDialog {

	private static DeviceDialog dialog;
	private HardwareWidget widget;

	private WidgetText wtTitle;
	private WidgetText wtName;
	private WidgetInput wiName;
	private WidgetText wtDescription;
	private WidgetArea waDescription;
	private WidgetButton wbSet;
	private WidgetButton wbCancel;

	private CometDeviceLoadResponse device;

	private DeviceDialog() {
		super(230, 250);
		// Label serial
		wtTitle = new WidgetText(210, "00-00", WidgetText.ALIGN_CENTER);
		addWidget(10, 10, wtTitle);
		// Name text
		wtName = new WidgetText(170, LocaleFactory.getLabels()
				.DeviceDialog_texName(), WidgetText.ALIGN_LEFT);
		addWidget(10, 40, wtName);
		// Name area
		wiName = new WidgetInput(120, 20);
		addWidget(100, 40, wiName);
		// Description text
		wtDescription = new WidgetText(170, LocaleFactory.getLabels()
				.DeviceDialog_texDescription(), WidgetText.ALIGN_LEFT);
		addWidget(10, 80, wtDescription);
		// Description area
		waDescription = new WidgetArea(210, 100);
		addWidget(10, 105, waDescription);
		// Button Set
		wbSet = new WidgetButton(LocaleFactory.getLabels()
				.DeviceDialog_butSet(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
				send();
			}
		});
		addWidget(50, 220, wbSet);
		// Button Cancel
		wbCancel = new WidgetButton(LocaleFactory.getLabels()
				.DeviceDialog_butCancel(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addWidget(140, 220, wbCancel);
	}

	public static void open(CometDeviceLoadResponse device, HardwareWidget widget) {
		if (dialog == null) {
			dialog = new DeviceDialog();
		}
		dialog.widget = widget;
		dialog.device = device;
		dialog.wtTitle.setText(device.getAddress() + " - " + device.getType());
		dialog.wiName.setText(device.getName());
		dialog.waDescription.setText(device.getDescription());
		dialog.center();
		dialog.show();
	}

	private void send() {
		CometDeviceSetMessage message = new CometDeviceSetMessage(
				device.getSerial(), device.getNumber());
		message.setType(device.getType());
		message.setAddress(device.getAddress());
		message.setName(wiName.getText());
		message.setDescription(waDescription.getText());
		CometSender.send(message, widget);
	}
}
