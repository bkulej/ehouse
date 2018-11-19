package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.CommandSender;
import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.ReceiveMapperValue;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class OutdoorDialog extends InfoDialog {

	private static OutdoorDialog dialog;

	private WidgetSwitchLarge wsTemperature;
	private WidgetSwitchLarge wsLightMeter;
	private WidgetSwitchLarge wsLightOutSquare;
	private WidgetSwitchLarge wsLightOutTerrace;
	private WidgetSwitchLarge wsLightOutHouse;
	private WidgetSwitchLarge wsLightOutGarage;
	private WidgetSwitchLarge wsGateOpen;
	private WidgetSwitchLarge wsGateClose;
	private WidgetSwitchLarge wsGateDoor;
	

	private CommandSender sender;

	private OutdoorDialog() {
		super(490, 490, LocaleFactory.getLabels().OutdoorDialog_texTitle());
		// 1sza linia
		wsTemperature = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtTemepersture());
		wsTemperature.addReceiver(new ReceiveMapperValue("OUT_TEMP_SEN","value"));
		addWidget(10, 10, wsTemperature);
		wsLightMeter = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtLightMeter());
		wsLightMeter.addReceiver(new ReceiveMapperValue("OUT_LIGHT_SEN","value"));
		addWidget(170, 10, wsLightMeter);
		wsLightOutSquare = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtLightOutSquare());
		wsLightOutSquare.addReceiver(new ReceiveMapperStatus("OUT_SQUARE_LIGHT","status", "01{on}other{off}"));
		wsLightOutSquare.setSender(new SendMapper("OUT_SQUARE_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(330, 10, wsLightOutSquare);
		// 2ga linia
		wsLightOutTerrace = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtLightOutTerrace());
		wsLightOutTerrace.addReceiver(new ReceiveMapperStatus("OUT_TERRACE_LIGHT","status", "01{on}other{off}"));
		wsLightOutTerrace.setSender(new SendMapper("OUT_TERRACE_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(10, 170, wsLightOutTerrace);
		wsLightOutHouse = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtLightOutHouse());
		wsLightOutHouse.addReceiver(new ReceiveMapperStatus("OUT_HOUSE_LIGHT","status", "01{on}other{off}"));
		wsLightOutHouse.setSender(new SendMapper("OUT_HOUSE_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(170, 170, wsLightOutHouse);
		wsLightOutGarage = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtLightOutGarage());
		wsLightOutGarage.addReceiver(new ReceiveMapperStatus("OUT_GARAGE_LIGHT","status", "01{on}other{off}"));
		wsLightOutGarage.setSender(new SendMapper("OUT_GARAGE_LIGHT","off{command=12;status=00;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		addWidget(330, 170, wsLightOutGarage);
		// 3ci linia
		wsGateOpen = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtGateOpen());
		wsGateOpen.addReceiver(new ReceiveMapperStatus("OUT_GATE","status", "01{on}other{off}"));
		wsGateOpen.setSender(new SendMapper("OUT_GATE","off{command=11;status=02;delay=00;}on{command=11;status=02;delay=00;}other{command=10;}"));
		addWidget(10, 330, wsGateOpen);
		wsGateClose = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtGateClose());
		wsGateClose.addReceiver(new ReceiveMapperStatus("OUT_GATE","status", "01{on}other{off}"));
		wsGateClose.setSender(new SendMapper("OUT_GATE","off{command=11;status=01;delay=00;}on{command=11;status=01;delay=00;}other{command=10;}"));
		addWidget(170, 330, wsGateClose);
		wsGateDoor = new WidgetSwitchLarge(LocaleFactory.getLabels().OutdoorDialog_swtGateDoor());
		wsGateDoor.addReceiver(new ReceiveMapperStatus("OUT_GATE","status", "01{on}other{off}"));
		wsGateDoor.setSender(new SendMapper("OUT_GATE","off{command=11;status=03;delay=00;}on{command=11;status=03;delay=00;}other{command=10;}"));
		addWidget(330, 330, wsGateDoor);
		
		// Sender
		sender = new CommandSender();
		sender.add("OUT_TEMP_SEN", "command=10;");
		sender.add("OUT_LIGHT_SEN", "command=10;");
		sender.add("OUT_SQUARE_LIGHT", "command=10;");
		sender.add("OUT_TERRACE_LIGHT", "command=10;");
		sender.add("OUT_HOUSE_LIGHT", "command=10;");
		sender.add("OUT_GARAGE_LIGHT", "command=10;");
		sender.add("OUT_GATE", "command=10;");
	}

	public static void open() {
		if (dialog == null) {
			dialog = new OutdoorDialog();
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
