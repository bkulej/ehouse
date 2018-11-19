package pl.eHouse.web.setup.client;

import java.util.List;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusResponse;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
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

public class ConfigWidget extends Composite implements CometInterface {

	private WidgetPanel panel;
	private WidgetTable<CometHardwareLoadResponse> tableHardware;
	private WidgetTable<CometEepromStatusResponse> tableStatus;
	WidgetButton wbLoad;
	WidgetButton wbEdit;

	public ConfigWidget() {
		super();
		// Panel
		panel = new WidgetPanel(1102, 535);
		// Tabela hardware
		tableHardware = new WidgetTable<CometHardwareLoadResponse>(
				new SingleSelectionModel<CometHardwareLoadResponse>(), 500, 500);
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabHardware_colSerial(), 80,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getSerial();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabHardware_colSoftware(), 60,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getSoftware();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabHardware_colAddress(), 60,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometHardwareLoadResponse>() {
					@Override
					public String getValue(CometHardwareLoadResponse response) {
						return response.getAddress();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabHardware_colDescription(), 300,
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
				clearStatus();
				loadStatus();
			}
		});
		panel.addWidget(0, 0, tableHardware);
		// Table device
		tableStatus = new WidgetTable<CometEepromStatusResponse>(
				new SingleSelectionModel<CometEepromStatusResponse>(), 550, 500);
		tableStatus.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabStatus_colPage(), 250,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometEepromStatusResponse>() {
					@Override
					public String getValue(CometEepromStatusResponse response) {
						return response.getPage();
					}
				});
		tableStatus.addColumn(LocaleFactory.getLabels()
				.ConfigWidget_tabStatus_colDevice(), 250,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometEepromStatusResponse>() {
					@Override
					public String getValue(CometEepromStatusResponse response) {
						return response.getDevice();
					}
				});
		panel.addWidget(550, 0, tableStatus);
		// Button load
		wbLoad = new WidgetButton(LocaleFactory.getLabels()
				.ConfigWidget_butLoad(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadHardwares();
			}
		});
		panel.addWidget(420, 510, wbLoad);
		// Button edit
		wbEdit = new WidgetButton(LocaleFactory.getLabels()
				.ConfigWidget_butEdit(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editStatus();
			}
		});
		panel.addWidget(1020, 510, wbEdit);
		// Inicjacja
		initWidget(panel);
		// CometListener.addListener(this);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometHardwareLoadResponse) {
			receiveHardware((CometHardwareLoadResponse) message);
		} else if (message instanceof CometEepromStatusResponse) {
			tableStatus.getList().add((CometEepromStatusResponse) message);
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

	private void loadHardwares() {
		tableHardware.getList().clear();
		tableStatus.getList().clear();
		CometSender.send(new CometHardwareLoadMessage(), this);
	}

	public void clearStatus() {
		tableStatus.getList().clear();
	}

	public void loadStatus() {
		CometHardwareLoadResponse row = ((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		CometEepromStatusMessage message = new CometEepromStatusMessage(
				row.getSerial());
		CometSender.send(message, this);
	}

	private void editStatus() {
		ConfigDialog
				.open(((SingleSelectionModel<CometHardwareLoadResponse>) tableHardware
						.getSelectionModel()).getSelectedObject(),
						((SingleSelectionModel<CometEepromStatusResponse>) tableStatus
								.getSelectionModel()).getSelectedObject(), this);
	}

}
