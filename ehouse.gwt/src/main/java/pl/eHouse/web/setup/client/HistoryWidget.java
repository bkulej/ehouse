package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryMessage;
import pl.eHouse.web.common.client.comet.messages.CometFindHistoryResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.PopupDate;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDate;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetPanel;
import pl.eHouse.web.common.client.widgets.WidgetTable;
import pl.eHouse.web.common.client.widgets.WidgetText;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.view.client.MultiSelectionModel;

public class HistoryWidget extends Composite implements CometInterface {

	private WidgetPanel panel;
	private WidgetTable<CometFindHistoryResponse> table;
	private WidgetText wtTitle;
	private WidgetDate wdStart;
	private WidgetDate wdStop;
	private WidgetInput wiAddresses;
	private WidgetInput wiCommands;
	private WidgetButton wbFind;

	public HistoryWidget() {
		// Panel
		panel = new WidgetPanel(1002, 535);
		// Tabela
		table = new WidgetTable<CometFindHistoryResponse>(
				new MultiSelectionModel<CometFindHistoryResponse>(), 1000, 500);
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colDate(), 200,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getDate();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colType(), 50,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getType();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colAdd(), 50,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getAdd();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colAsd(), 50,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getAsd();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colCommand(), 50,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getCommand();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colId(), 50,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getId();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.HistoryWidget_tabMessage_colData(), 450,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometFindHistoryResponse>() {
					@Override
					public String getValue(CometFindHistoryResponse message) {
						return message.getData();
					}
				});
		panel.addWidget(0, 0, table);
		// Tytu³
		wtTitle = new WidgetText(270, LocaleFactory.getLabels()
				.HistoryWidget_texTitle(), WidgetText.ALIGN_LEFT);
		panel.addWidget(0, 510, wtTitle);
		// Pole daty start
		wdStart = new WidgetDate(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PopupDate.getDate(wdStart, wdStart.getAbsoluteLeft(),
						wdStart.getAbsoluteTop() - 180);
			}
		});
		panel.addWidget(280, 510, wdStart);
		// Pole daty stop
		wdStop = new WidgetDate(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PopupDate.getDate(wdStop, wdStop.getAbsoluteLeft(),
						wdStop.getAbsoluteLeft() - 180);
			}
		});
		panel.addWidget(390, 510, wdStop);
		// Pole adresu
		wiAddresses = new WidgetInput(200);
		panel.addWidget(500, 510, wiAddresses);
		// Pole komendy
		wiCommands = new WidgetInput(200);
		panel.addWidget(710, 510, wiCommands);
		// Button Clear
		wbFind = new WidgetButton(LocaleFactory.getLabels()
				.HistoryWidget_butFind(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				send();
			}
		});
		panel.addWidget(920, 510, wbFind);
		// Inicjacja
		initWidget(panel);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometFindHistoryResponse) {
			table.getList().add((CometFindHistoryResponse) message);
		}
	}

	private void send() {
		table.getList().clear();
		CometSender.send(
				new CometFindHistoryMessage(wdStart.getTextDate(), wdStop
						.getTextDate(), wiAddresses.getText(), wiCommands
						.getText()), this);
	}

}
