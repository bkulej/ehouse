package pl.eHouse.web.setup.client;

import java.util.List;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometBootModeMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
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

public class ExploreWidget extends Composite implements CometInterface {

	private ExploreWidget widget = this;
	private WidgetPanel panel;	
	private WidgetTable<CometNetworkExploreResponse> tableHardware;
	WidgetInput wiSerial;
	WidgetButton wbFind;
	WidgetButton wbExplore;
	WidgetButton wbBoot;
	WidgetButton wbEdit;
	WidgetButton wbClear;

	public ExploreWidget() {
		// Panel
		panel = new WidgetPanel(1002, 535);
		// Tabela
		tableHardware = new WidgetTable<CometNetworkExploreResponse>(
				new SingleSelectionModel<CometNetworkExploreResponse>(), 1000,
				500);
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colSerial(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getSerial();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colHardware(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getHardware();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colSoftware(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getSoftware();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colAddress(), 100,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getAddress();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colVersion(), 200,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getVersion();
					}
				});
		tableHardware.addColumn(LocaleFactory.getLabels()
				.ExploreWidget_tabHardware_colDescription(), 400,
				HasHorizontalAlignment.ALIGN_LEFT,
				new TextColumn<CometNetworkExploreResponse>() {
					@Override
					public String getValue(CometNetworkExploreResponse response) {
						return response.getDescription();
					}
				});
		panel.addWidget(0, 0, tableHardware);
		// Input serial
		wiSerial = new WidgetInput(100, 8);
		panel.addWidget(450, 510, wiSerial);
		// Button Find
		wbFind = new WidgetButton(LocaleFactory.getLabels()
				.ExploreWidget_butFind(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				find();
			}
		});
		panel.addWidget(560, 510, wbFind);
		// Button Explore
		wbExplore = new WidgetButton(LocaleFactory.getLabels()
				.ExploreWidget_butExplore(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CometSender.send(new CometNetworkExploreMessage(),widget);
			}
		});
		panel.addWidget(650, 510, wbExplore);
		// Button Boot
		wbBoot = new WidgetButton(LocaleFactory.getLabels()
				.ExploreWidget_butBoot(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boot();
			}
		});
		panel.addWidget(740, 510, wbBoot);
		// Button Edit
		wbEdit = new WidgetButton(LocaleFactory.getLabels()
				.ExploreWidget_butEdit(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				edit();
			}
		});
		panel.addWidget(830, 510, wbEdit);
		// Button Clear
		wbClear = new WidgetButton(LocaleFactory.getLabels()
				.ExploreWidget_butClear(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tableHardware.getList().clear();
			}
		});
		panel.addWidget(920, 510, wbClear);
		// Inicjacja
		initWidget(panel);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometNetworkExploreResponse) {
			// Odpowiedz explore
			receiveExplore((CometNetworkExploreResponse) message);
		} else if (message instanceof CometNetworkAllocateResponse) {
			// Odpowiedz zmiany adresu
			receiveAllocate((CometNetworkAllocateResponse) message);
		}
	}

	private void receiveExplore(CometNetworkExploreResponse response) {
		List<CometNetworkExploreResponse> list = tableHardware.getList();
		boolean found = false;
		for (CometNetworkExploreResponse item : list) {
			if (item.getSerial().equals(response.getSerial())) {
				item.setHardware(response.getHardware());
				item.setSoftware(response.getSoftware());
				item.setAddress(response.getAddress());
				item.setDescription(response.getDescription());
				tableHardware.refresh();
				found = true;
				break;
			}
		}
		if (!found) {
			list.add(response);
		}
	}
	
	private void receiveAllocate(CometNetworkAllocateResponse response) {
		List<CometNetworkExploreResponse> list = tableHardware.getList();
		for (CometNetworkExploreResponse item : list) {
			if (item.getSerial().equals(response.getSerial())) {
				item.setHardware(response.getHardware());
				item.setSoftware(response.getSoftware());
				item.setAddress(response.getAddress());
				item.setDescription(response.getDescription());
				tableHardware.refresh();
				break;
			}
		}
	}

	private void find() {
		try {
			Validator.checkSerial(wiSerial.getText());
			CometSender
					.send(new CometNetworkExploreMessage(wiSerial.getText()),this);
		} catch (ValidatorException e) {
			PopupMessage.showWarning(e.getMessage());
		}
	}

	private void edit() {
		CometNetworkExploreResponse row = ((SingleSelectionModel<CometNetworkExploreResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			HardwareDialog.open(row,this);
		}
	}

	private void boot() {
		CometNetworkExploreResponse row = ((SingleSelectionModel<CometNetworkExploreResponse>) tableHardware
				.getSelectionModel()).getSelectedObject();
		if (row != null) {
			PopupMessage.showQuestion(LocaleFactory.getMessages()
					.ExploreWidget_bootHardware(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					PopupMessage.close();
					CometNetworkExploreResponse row = ((SingleSelectionModel<CometNetworkExploreResponse>) tableHardware
							.getSelectionModel()).getSelectedObject();
					CometBootModeMessage message = new CometBootModeMessage(row
							.getSerial(), row.getSoftware(), row.getHardware());
					CometSender.send(message,widget);
				}
			});
		}
	}

}
