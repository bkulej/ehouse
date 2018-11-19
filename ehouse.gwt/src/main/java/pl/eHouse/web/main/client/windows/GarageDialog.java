package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.CommandSender;
import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.ReceiveMapperValue;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class GarageDialog extends InfoDialog {

	private static GarageDialog dialog;

	private WidgetSwitchLarge wsGateOpen;
	private WidgetSwitchLarge wsGateClose;
	private WidgetSwitchLarge wsGateDoor;
	private WidgetSwitchLarge wsLightInGarage;
	private WidgetSwitchLarge wsLightInBoiler;

	private CommandSender sender;

	private GarageDialog() {
		super(490, 330, LocaleFactory.getLabels().GarageDialog_texTitle());
		// 1sza linia
		wsGateOpen = new WidgetSwitchLarge(LocaleFactory.getLabels().GarageDialog_swtGateOpen());
		wsGateOpen.addReceiver(new ReceiveMapperStatus("GARAGE_GATE","status", "01{on}other{off}"));
		wsGateOpen.setSender(new SendMapper("GARAGE_GATE","off{command=11;status=02;delay=00;}on{command=11;status=02;delay=00;}other{command=10;}"));
		addWidget(10, 10, wsGateOpen);
		wsGateClose = new WidgetSwitchLarge(LocaleFactory.getLabels().GarageDialog_swtGateClose());
		wsGateClose.addReceiver(new ReceiveMapperStatus("GARAGE_GATE","status", "01{on}other{off}"));
		wsGateClose.setSender(new SendMapper("GARAGE_GATE","off{command=11;status=01;delay=00;}on{command=11;status=01;delay=00;}other{command=10;}"));
		addWidget(170, 10, wsGateClose);
		wsGateDoor = new WidgetSwitchLarge(LocaleFactory.getLabels().GarageDialog_swtGateDoor());
		wsGateDoor.addReceiver(new ReceiveMapperStatus("GARAGE_GATE","status", "01{on}other{off}"));
		wsGateDoor.setSender(new SendMapper("GARAGE_GATE","off{command=11;status=03;delay=00;}on{command=11;status=03;delay=00;}other{command=10;}"));
		addWidget(330, 10, wsGateDoor);
		// 2ga linia
		wsLightInGarage = new WidgetSwitchLarge(LocaleFactory.getLabels().GarageDialog_swtGarage());
		wsLightInGarage.addReceiver(new ReceiveMapperStatus("GARAGE_LIGHT","status", "01{on}other{off}"));
		wsLightInGarage.addReceiver(new ReceiveMapperValue("GARAGE_TEMP_SEN","value"));
		wsLightInGarage.setSender(new SendMapper("GARAGE_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(10, 170, wsLightInGarage);
		wsLightInBoiler = new WidgetSwitchLarge(LocaleFactory.getLabels().GarageDialog_swtBoiler());
		wsLightInBoiler.addReceiver(new ReceiveMapperStatus("BOILER_LIGHT","status", "01{on}other{off}"));
		wsLightInBoiler.addReceiver(new ReceiveMapperValue("BOILER_TEMP_SEN","value"));
		wsLightInBoiler.setSender(new SendMapper("BOILER_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(170, 170, wsLightInBoiler);
		
		// Sender
		sender = new CommandSender();
		sender.add("GARAGE_GATE", "command=10;");
		sender.add("GARAGE_LIGHT", "command=10;");
		sender.add("GARAGE_TEMP_SEN", "command=10;");
		sender.add("BOILER_LIGHT", "command=10;");
		sender.add("BOILER_TEMP_SEN", "command=10;");
	}

	public static void open() {
		if (dialog == null) {
			dialog = new GarageDialog();
		}
		dialog.showDialog();
		dialog.center();
		dialog.show();

	}

	@Override
	protected void showDialog() {
		dialog.sender.start();

	}

	@Override
	protected void hideDialog() {
		dialog.sender.stop();
	}

}
