package pl.eHouse.web.setup.client;

import java.util.ArrayList;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadFinish;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.comet.messages.CometEepromSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusResponse;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetList;
import pl.eHouse.web.common.client.widgets.WidgetText;
import pl.eHouse.web.setup.client.config.ConfigPanel;
import pl.eHouse.web.setup.client.config.EmptyConfigPanel;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ConfigDialog extends WidgetDialog implements CometInterface {

	private static ConfigDialog dialog;

	ConfigWidget parent;
	private WidgetText wtTitle;
	private WidgetText wtDevice;
	private WidgetList<CometDeviceLoadResponse> wlDevice;
	private WidgetButton wbClose;
	private WidgetButton wbSave;
	private ArrayList<ConfigPanel> panels = new ArrayList<ConfigPanel>();
	private ConfigPanel activePanel;

	private CometEepromStatusResponse status;
	private CometHardwareLoadResponse hardware;
	private CometDeviceLoadResponse device;

	private int currentDeviceNumber;

	private ConfigDialog() {
		super(400, 540);
		// Label serial
		wtTitle = new WidgetText(380, "", WidgetText.ALIGN_CENTER);
		addWidget(10, 10, wtTitle);
		wtDevice = new WidgetText(50, LocaleFactory.getLabels()
				.ConfigDialog_texDevice(), WidgetText.ALIGN_RIGHT);
		addWidget(10, 40, wtDevice);
		// Dodanie devices
		wlDevice = new WidgetList<CometDeviceLoadResponse>(290,
				new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent event) {
						selectPanel();
					}
				});
		addWidget(70, 40, wlDevice);
		// Button Save
		wbSave = new WidgetButton(LocaleFactory.getLabels()
				.ConfigDialog_butSave(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveConfig();
			}
		});
		addWidget(220, 510, wbSave);
		// Button Close
		wbClose = new WidgetButton(LocaleFactory.getLabels()
				.ConfigDialog_butClose(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addWidget(310, 510, wbClose);
		// Dodanie paneli konfiguracyjnych
		ConfigPanel.addPanels(0, 105, this, panels);
		// for (ConfigPanel panel : panels) {
		// addWidget(0, 105, panel);
		// }
	}

	public static void open(CometHardwareLoadResponse hardware,
			CometEepromStatusResponse status, ConfigWidget parent) {
		if (dialog == null) {
			dialog = new ConfigDialog();
		}
		dialog.status = status;
		dialog.hardware = hardware;
		dialog.parent = parent;
		dialog.activePanel = null;
		dialog.wtTitle.setText(hardware.getSerial() + " - " + status.getPage());
		dialog.loadDevices();
		dialog.center();
		dialog.show();
	}

	/**
	 * Wys³anie komunikatu pobrania device
	 */
	private void loadDevices() {
		wlDevice.clear();
		wlDevice.addItem(LocaleFactory.getLabels().ConfigDialog_valNoConfig(),
				new CometDeviceLoadResponse(hardware.getSerial(),
						EmptyConfigPanel.supportedDevice,
						EmptyConfigPanel.supportedType));
		currentDeviceNumber = 0;
		CometSender.send(new CometDeviceLoadMessage(hardware.getAddress()),
				this);
	}

	/**
	 * Wyslanie komunikatu do wyslania configa
	 */
	private void loadConfig() {
		CometSender.send(
				new CometEepromGetMessage(hardware.getSerial(), status
						.getPage()), this);
	}

	/**
	 * Otrzymanie wiadomosci
	 */
	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometDeviceLoadResponse) {
			receiveDevice((CometDeviceLoadResponse) message);
		} else if (message instanceof CometEepromGetResponse) {
			receiveConfig((CometEepromGetResponse) message);
		} else if (message instanceof CometDeviceLoadFinish) {
			wlDevice.setSelectedIndex(currentDeviceNumber);
			selectPanel();
			loadConfig();
		}
	}

	/**
	 * Odebranie device
	 * 
	 * @param response
	 */
	private void receiveDevice(CometDeviceLoadResponse response) {
		if (status.getDevice().equals(response.getNumber())) {
			currentDeviceNumber = wlDevice.getItemCount();
		}
		wlDevice.addItem(response.getAddress() + " - " + response.getTypeName()
				+ ", " + response.getDescription(), response);
	}

	/**
	 * Odebranie config
	 * 
	 * @param message
	 */
	private void receiveConfig(CometEepromGetResponse message) {
		for (ConfigPanel panel : panels) {
			panel.init(message);
		}
	}

	/**
	 * Wybranie i wyswietlenie panelu
	 */
	private void selectPanel() {
		device = wlDevice.getSelectedItem();
		if (device != null) {
			activePanel = null;
			for (ConfigPanel panel : panels) {
				if (panel.checkAndSet(device)) {
					activePanel = panel;
				}
			}
		}
	}

	/**
	 * Zapisanie konfiguracji
	 */
	private void saveConfig() {
		if (activePanel != null) {
			CometEepromSetMessage message = new CometEepromSetMessage();
			try {
				message.setSerial(hardware.getSerial());
				message.setAddress(hardware.getAddress());
				message.setPage(status.getPage());
				message.setDevice(device.getNumber());
				message.setType(activePanel.getType());
				message.setValues(activePanel.save());
				parent.clearStatus();
				CometSender.send(message, parent);
				hide();
			} catch (ValidatorException e) {
				PopupMessage.showError(e.getMessage());
			}
		}
	}

}
