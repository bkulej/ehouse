package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometListener;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometApiMessage;
import pl.eHouse.web.common.client.comet.messages.CometSendMessage;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetButton;
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

public class MessageWidget extends Composite implements CometInterface {

	private WidgetPanel panel;
	private WidgetTable<CometApiMessage> table;
	private WidgetText wtTitle;
	private WidgetInput wiCommand;
	private WidgetInput wiAddress;
	private WidgetInput wiData;
	private WidgetButton wbSend;
	private WidgetButton wbClear;

	public MessageWidget() {
		// Panel
		panel = new WidgetPanel(1002, 535);
		// Tabela
		table = new WidgetTable<CometApiMessage>(
				new MultiSelectionModel<CometApiMessage>(), 1000, 500);
		table.addColumn(LocaleFactory.getLabels()
				.MessageWidget_tabMessage_colDate(), 200,
				HasHorizontalAlignment.ALIGN_CENTER,
				new TextColumn<CometApiMessage>() {
					@Override
					public String getValue(CometApiMessage message) {
						return message.getDate();
					}
				});
		table.addColumn(LocaleFactory.getLabels()
				.MessageWidget_tabMessage_colMessage(), 800,
				HasHorizontalAlignment.ALIGN_LEFT,
				new TextColumn<CometApiMessage>() {
					@Override
					public String getValue(CometApiMessage message) {
						return message.getValue();
					}
				});
		panel.addWidget(0, 0, table);
		// Tytu³
		wtTitle = new WidgetText(160, LocaleFactory.getLabels()
				.MessageWidget_texTitle(), WidgetText.ALIGN_LEFT);
		panel.addWidget(0, 510, wtTitle);
		// Pole komendy
		wiCommand = new WidgetInput(30);
		wiCommand.setText("10");
		panel.addWidget(170, 510, wiCommand);
		// Pole adresu
		wiAddress = new WidgetInput(50);
		wiAddress.setText("0110");
		panel.addWidget(210, 510, wiAddress);
		// Pole danych
		wiData = new WidgetInput(100);
		panel.addWidget(270, 510, wiData);
		// Button Send
		wbSend = new WidgetButton(LocaleFactory.getLabels()
				.MessageWidget_butSend(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				send();
			}
		});
		panel.addWidget(380, 510, wbSend);
		// Button Clear
		wbClear = new WidgetButton(LocaleFactory.getLabels()
				.MessageWidget_butClear(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				table.getList().clear();
			}
		});
		panel.addWidget(920, 510, wbClear);
		// Inicjacja
		initWidget(panel);
		CometListener.setApiListener(this);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometApiMessage) {
			table.getList().add(0, (CometApiMessage) message);
		}
	}

	private void send() {
		try {
			Validator.checkByteHex(wiCommand.getText(), LocaleFactory
					.getMessages().MessageWidget_badCommand());
			Validator.checkWordHex(wiAddress.getText(), LocaleFactory
					.getMessages().MessageWidget_badAddress());
			Validator.checkDataHex(wiData.getText(), LocaleFactory
					.getMessages().MessageWidget_badData());
			CometSender.send(new CometSendMessage(wiCommand.getText(),
					wiAddress.getText(), wiData.getText()), this);
		} catch (ValidatorException e) {
			PopupMessage.showWarning(e.getMessage());
		}
	}

}
