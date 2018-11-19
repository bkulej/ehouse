package pl.eHouse.web.setup.client;

import java.util.List;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometBootModeMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetResponse;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateResponse;
import pl.eHouse.web.common.client.comet.messages.CometRestartMessage;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ClientConst;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetPanel;
import pl.eHouse.web.common.client.widgets.WidgetTable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class HardwareWidget extends Composite implements CometInterface {

	private HardwareWidget widget = this;
	private WidgetPanel panel;
	private WidgetTable<CometHardwareLoadResponse> tableHardware;
	private WidgetTable<CometDeviceLoadResponse> tableDevice;
	WidgetButton wbRestart;
	WidgetButton wbBoot;
	WidgetButton wbLoad;
	WidgetButton wbDelete;
	WidgetButton wbEdit;
	WidgetButton wbReaddress;

	public HardwareWidget() {
		super();
		// Panel
		panel = new WidgetPanel(1102, 535);
		// Tabela hardware
		tableHardware = new WidgetTable<CometHardwareLoadResponse>(
				new SingleSelectionModel<CometHardwareLoadResponse>(), 500, 500);
		tableHardware.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabHardware_colSerial(), 80,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getSerial();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabHardware_colSoftware(), 60,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getSoftware();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabHardware_colAddress(), 60,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getAddress();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabHardware_colDescription(), 300,
				HasHorizontalAlignment.ALIGN_LEFT,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getDescription();
					}
				});
		tableHardware.addSelectionHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				loadDevices();
			}
		});
		panel.addWidget(0, 0, tableHardware);
		// Table device
		tableDevice = new WidgetTable<CometDeviceLoadResponse>(
				new SingleSelectionModel<CometDeviceLoadResponse>(), 550, 500);
		tableDevice.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabDevice_colAddress(), 70,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometDeviceLoadResponse>() {
					@Override
					public String getValue(CometDeviceLoadResponse response) {
						return response.getAddress();
					}
				});
		tableDevice.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabDevice_colType(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometDeviceLoadResponse>() {
					@Override
					public String getValue(CometDeviceLoadResponse response) {
						return response.getTypeName();
					}
				});
		tableDevice.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabDevice_colName(), 150,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometDeviceLoadResponse>() {
					@Override
					public String getValue(CometDeviceLoadResponse response) {
						return response.getName();
					}
				});
		tableDevice.addColumn(LocaleFactory.getLabels()
				.HardwareWidget_tabDevice_colDescription(), 230,
				HasHorizontalAlignment.ALIGN_LEFT,
				new TextColumn<CometDeviceLoadResponse>() {
					@Override
					public String getValue(CometDeviceLoadResponse response) {
						return response.getDescription();
					}
				});
		panel.addWidget(550, 0, tableDevice);
		// Button reset
		wbRestart = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butRestart(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				restartHardware();
			}
		});
		panel.addWidget(0, 510, wbRestart);
		// Button boot
		wbBoot = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butBoot(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bootHardware();
			}
		});
		panel.addWidget(90, 510, wbBoot);
		// Button reall
		wbReaddress = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butReaddress(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				readdressHardware();
			}
		});
		panel.addWidget(180, 510, wbReaddress);
		// Button delete
		wbDelete = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butDelete(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteHardware();
			}
		});
		panel.addWidget(330, 510, wbDelete);
		// Button load
		wbLoad = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butLoad(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadHardwares();
			}
		});
		panel.addWidget(420, 510, wbLoad);
		// Button edit
		wbEdit = new WidgetButton(LocaleFactory.getLabels()
				.HardwareWidget_butEdit(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editDevice();
			}
		});
		panel.addWidget(1020, 510, wbEdit);
		// Inicjacja
		initWidget(panel);
		// CometListener.addPernamentListener(this);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometHardwareLoadResponse) {
			receiveHardware((CometHardwareLoadResponse) message);
		} else if (message instanceof CometNetworkAllocateResponse) {
			loadHardwares();
		} else if (message instanceof CometDeviceLoadResponse) {
			tableDevice.getList().add((CometDeviceLoadResponse) message);
		} else if (message instanceof CometDeviceSetResponse) {
			receiveDeviceSet((CometDeviceSetResponse) message);
		}
	}

	private void receiveHardware(CometHardwareLoadResponse response) {
		List<CometHardwareLoadResponse> list = tableHardware.getList();
		boolean found = false;
		for (CometHardwareLoadResponse row : list) {
			if (response.getSerial().equals(row.getSerial())) {
				row.setAddress(response.getAddress());
				row.setDescription(response.getDescription());
				found = true;
				break;
			}
		}
		if (found) {
			tableHardware.refresh();
		} else {
			list.add(response);
		}
	}

	public void receiveDeviceSet(CometDeviceSetResponse response) {
		for (CometDeviceLoadResponse row : tableDevice.getList()) {
			if (row.getAddress().equals(response.getAddress())) {
				row.setName(response.getName());
				row.setDescription(response.getDescription());
				tableDevice.refresh();
				break;
			}
		}
	}

	private void loadDevices() {
		tableDevice.getList().clear();
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		CometSender.send(new CometDeviceLoadMessage(row.getAddress()), this);
	}

	private void loadHardwares() {
		tableHardware.getList().clear();
		tableDevice.getList().clear();
		CometSender.send(new CometHardwareLoadMessage(), this);
	}

	private void restartHardware() {
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.HardwareWidget_restartHardware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
							.getSelectionModel()).getSelectedObject();
					CometRestartMessage message = new CometRestartMessage(row
							.getSerial(), row.getSoftware(), row.getHardware());
					CometSender.send(message, widget);
				}
			});
		}
	}

	private void bootHardware() {
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.HardwareWidget_bootHardware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
							.getSelectionModel()).getSelectedObject();
					CometBootModeMessage message = new CometBootModeMessage(row
							.getSerial(), row.getSoftware(), row.getHardware());
					CometSender.send(message, widget);
				}
			});
		}
	}

	private void deleteHardware() {
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.HardwareWidget_deleteHardware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
							.getSelectionModel()).getSelectedObject();
					CometNetworkAllocateMessage message = new CometNetworkAllocateMessage(
							row.getSerial(), row.getSoftware(), row
									.getHardware());
					message.setAddress(ClientConst.ADDRESS_EMPTY);
					message.setDescription(row.getDescription());
					CometSender.send(message, widget);
				}
			});
		}
	}
	
	private void readdressHardware() {
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.HardwareWidget_readdressHardware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
							.getSelectionModel()).getSelectedObject();
					CometNetworkAllocateMessage message = new CometNetworkAllocateMessage(
							row.getSerial(), row.getSoftware(), row
									.getHardware());
					message.setAddress(row.getAddress());
					message.setDescription(CometNetworkAllocateMessage.MESS_REALLOCATE);
					CometSender.send(message, widget);
				}
			});
		}
	}

	private void editDevice() {
		CometDeviceLoadResponse row = ((SingleSelectionModel<CometDeviceLoadResponse>) tableDevice
				.getSelectionModel()).getSelectedObject();
		DeviceDialog.open(row, this);
	}

}
