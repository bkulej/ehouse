package pl.eHouse.web.setup.client;

import java.util.List;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometBootFindMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootFindResponse;
import pl.eHouse.web.common.client.comet.messages.CometBootStartMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareDeleteMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ClientConst;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetPanel;
import pl.eHouse.web.common.client.widgets.WidgetTable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.SingleSelectionModel;

public class BootloaderWidget extends Composite implements CometInterface {

	private BootloaderWidget widget = this;
	private WidgetPanel panel;
	private WidgetTable<CometBootFindResponse> tableHardware;
	WidgetButton wbStart;
	WidgetInput wiSerial;
	WidgetButton wbFind;
	WidgetButton wbClear;
	private WidgetTable<CometSoftwareLoadResponse> tableSoftware;
	WidgetButton wbSave;
	WidgetButton wbUpload;
	WidgetButton wbLoad;
	WidgetButton wbDelete;

	public BootloaderWidget() {
		super();
		// Panel
		panel = new WidgetPanel(1102, 535);
		// Tabela hardware
		tableHardware = new WidgetTable<CometBootFindResponse>(
				new SingleSelectionModel<CometBootFindResponse>(), 500, 500);
		tableHardware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabHardware_colSerial(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometBootFindResponse>() {
					@Override
					public String getValue(CometBootFindResponse response) {
						return response.getSerial();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabHardware_colHardware(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometBootFindResponse>() {
					@Override
					public String getValue(CometBootFindResponse response) {
						return response.getHardwareType();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabHardware_colMemory(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometBootFindResponse>() {
					@Override
					public String getValue(CometBootFindResponse response) {
						return response.getMemorySize();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabHardware_colPage(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometBootFindResponse>() {
					@Override
					public String getValue(CometBootFindResponse response) {
						return response.getPageSize();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabHardware_colStatus(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometBootFindResponse>() {
					@Override
					public String getValue(CometBootFindResponse response) {
						return response.getStatus();
					}
				});
		panel.addWidget(0, 0, tableHardware);
		// Button start
		wbStart = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butStart(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startHardware();
			}
		});
		panel.addWidget(0, 510, wbStart);
		// Input serial
		wiSerial = new WidgetInput(100, 8);
		panel.addWidget(220, 510, wiSerial);
		// Button Find
		wbFind = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butFind(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				findHardware();
			}
		});
		panel.addWidget(330, 510, wbFind);
		// Button Clear
		wbClear = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butClear(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tableHardware.getList().clear();
			}
		});
		panel.addWidget(420, 510, wbClear);
		// Table device
		tableSoftware = new WidgetTable<CometSoftwareLoadResponse>(
				new SingleSelectionModel<CometSoftwareLoadResponse>(), 550, 500);
		tableSoftware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabSoftware_colDate(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometSoftwareLoadResponse>() {
					@Override
					public String getValue(CometSoftwareLoadResponse response) {
						return response.getDate();
					}
				});
		tableSoftware.addColumn(LocaleFactory.getLabels()
				.BootloaderWidget_tabSoftware_colDesc(), 450,
				HasHorizontalAlignment.ALIGN_LEFT,
				new TextColumn<CometSoftwareLoadResponse>() {
					@Override
					public String getValue(CometSoftwareLoadResponse response) {
						return response.getDescription();
					}
				});
		panel.addWidget(550, 0, tableSoftware);
		// Button save
		wbSave = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butSave(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveSoftware();
			}
		});
		panel.addWidget(550, 510, wbSave);
		// Button delete
		wbDelete = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butDelete(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteSoftware();
			}
		});
		panel.addWidget(840, 510, wbDelete);
		// Button upload
		wbUpload = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butUpload(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UploadDialog.open(ClientConst.UPLOAD_FIELD_TYPE_HEX);
			}
		});
		panel.addWidget(930, 510, wbUpload);
		// Button load
		wbLoad = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderWidget_butLoad(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadSoftware();
			}
		});
		panel.addWidget(1020, 510, wbLoad);
		// Inicjacja
		initWidget(panel);
		// CometListener.addListener(this);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometSoftwareLoadResponse) {
			// Odpowiedz wczytania sotware
			tableSoftware.getList().add((CometSoftwareLoadResponse) message);
		} else if (message instanceof CometBootFindResponse) {
			// Odpowiedz zmiany adresu
			receiveBootFind((CometBootFindResponse) message);
		}
	}

	private void receiveBootFind(CometBootFindResponse response) {
		List<CometBootFindResponse> list = tableHardware.getList();
		boolean found = false;
		for (CometBootFindResponse item : list) {
			if (item.getSerial().equals(response.getSerial())) {
				item.setStatus(response.getStatus());
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

	private void startHardware() {
		CometBootFindResponse row = ((SingleSelectionModel<CometBootFindResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row == null) {
			PopupMessage.showWarning(LocaleFactory.getMessages()
					.BootloaderWidget_noHardware());
			return;
		}
		if (!row.getStatus().equals(CometBootFindResponse.STATUS_BOOT)) {
			PopupMessage.showWarning(LocaleFactory.getMessages()
					.BootloaderWidget_noDebugMode());
			return;
		}
		PopupMessage.showQuestion(LocaleFactory.getMessages()
				.BootloaderWidget_startHardware(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PopupMessage.close();
				CometBootFindResponse row = ((SingleSelectionModel<CometBootFindResponse>) tableHardware
						.getSelectionModel()).getSelectedObject();
				CometSender.send(
						new CometBootStartMessage(row.getSerial(), row
								.getHardwareType(), row.getMemorySize(), row
								.getPageSize(), row.getStatus()), widget);
			}
		});

	}

	private void findHardware() {
		try {
			CometSender.send(
					new CometBootFindMessage(Validator.checkSerial(wiSerial
							.getText())), widget);
		} catch (ValidatorException e) {
			PopupMessage.showWarning(e.getMessage());
		}
	}

	private void loadSoftware() {
		tableSoftware.getList().clear();
		CometSender.send(new CometSoftwareLoadMessage(), widget);
	}

	private void deleteSoftware() {
		CometSoftwareLoadResponse row = ((SingleSelectionModel<CometSoftwareLoadResponse>) tableSoftware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.BootloaderWidget_deleteSoftware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometSoftwareLoadResponse row = ((SingleSelectionModel<CometSoftwareLoadResponse>) tableSoftware
							.getSelectionModel()).getSelectedObject();
					tableSoftware.getList().clear();
					CometSender.send(
							new CometSoftwareDeleteMessage(row.getId(), row
									.getType(), row.getName(), row
									.getDescription(), row.getDate()), widget);
				}
			});
		}
	}

	private void saveSoftware() {
		CometBootFindResponse hardware = ((SingleSelectionModel<CometBootFindResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (hardware == null) {
			PopupMessage.showWarning(LocaleFactory.getMessages()
					.BootloaderWidget_noHardware());
			return;
		}
		CometSoftwareLoadResponse software = ((SingleSelectionModel<CometSoftwareLoadResponse>) tableSoftware
				.getSelectionModel()).getSelectedObject();
		if (software == null) {
			PopupMessage.showWarning(LocaleFactory.getMessages()
					.BootloaderWidget_noSoftware());
			return;
		}
		BootloaderDialog.open(hardware, software);
	}

}
