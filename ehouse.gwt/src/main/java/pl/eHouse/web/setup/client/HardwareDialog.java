package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressMessage;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ClientConst;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetArea;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetList;
import pl.eHouse.web.common.client.widgets.WidgetText;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class HardwareDialog extends WidgetDialog implements CometInterface {

	private static HardwareDialog dialog;

	private ExploreWidget widget;
	private WidgetText wtTitle;
	private WidgetText wtAddress;
	private WidgetList<String> wlAddress;
	private WidgetText wtDescription;
	private WidgetArea waDescription;
	private WidgetButton wbSet;
	private WidgetButton wbCancel;

	private CometNetworkExploreResponse hardware;

	private HardwareDialog() {
		super(230, 260);
		// Label serial
		wtTitle = new WidgetText(210, "00000000", WidgetText.ALIGN_CENTER);
		addWidget(10, 10, wtTitle);
		// Address label
		wtAddress = new WidgetText(110, LocaleFactory.getLabels()
				.HardwareDialog_texAddress(), WidgetText.ALIGN_LEFT);
		addWidget(10, 50, wtAddress);
		// Address list
		wlAddress = new WidgetList<String>(95);
		wlAddress.addItem("");
		addWidget(125, 50, wlAddress);
		// Name text
		wtDescription = new WidgetText(170, LocaleFactory.getLabels()
				.HardwareDialog_texDescription(), WidgetText.ALIGN_LEFT);
		addWidget(10, 90, wtDescription);
		// Name area
		waDescription = new WidgetArea(210, 100);
		addWidget(10, 115, waDescription);
		// Button Set
		wbSet = new WidgetButton(LocaleFactory.getLabels()
				.HardwareDialog_butSet(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					Validator.checkString(waDescription.getText(),
							LocaleFactory.getMessages()
									.HardwareDialog_emptyDescription());
					hide();
					send();
				} catch (ValidatorException e) {
					PopupMessage.showWarning(e.getMessage());
				}
			}
		});
		addWidget(50, 230, wbSet);
		// Button Cancel
		wbCancel = new WidgetButton(LocaleFactory.getLabels()
				.HardwareDialog_butCancel(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addWidget(140, 230, wbCancel);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometFreeAddressResponse) {
			for (String address : ((CometFreeAddressResponse) message)
					.getAddresses()) {
				wlAddress.addItem(address);
			}
		}
	}

	public static void open(CometNetworkExploreResponse hardware,
			ExploreWidget widget) {
		if (dialog == null) {
			dialog = new HardwareDialog();
		}
		dialog.hardware = hardware;
		dialog.widget = widget;
		dialog.wtTitle.setText(hardware.getSerial() + " - "
				+ hardware.getAddress().toString());
		dialog.waDescription.setText(hardware.getDescription());
		dialog.wlAddress.clear();
		dialog.wlAddress.addItem(ClientConst.ADDRESS_NOCHANGE);
		CometSender.send(new CometFreeAddressMessage(), dialog);
		dialog.center();
		dialog.show();
	}

	private void send() {
		CometNetworkAllocateMessage message = new CometNetworkAllocateMessage(
				hardware.getSerial(), hardware.getSoftware(),
				hardware.getHardware());
		message.setDescription(waDescription.getText());
		String address = wlAddress.getItemText(wlAddress.getSelectedIndex());
		if (address.equals(ClientConst.ADDRESS_NOCHANGE)) {
			message.setAddress(hardware.getAddress());
		} else {
			message.setAddress(address);
		}
		CometSender.send(message, widget);
	}

}
