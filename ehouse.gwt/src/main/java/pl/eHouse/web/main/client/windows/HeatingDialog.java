package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.CommandSender;
import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.ReceiveMapperValue;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.PopupInputNumericLarge;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class HeatingDialog extends InfoDialog {

	private static HeatingDialog dialog;

	private WidgetSwitchLarge wsRadiatorSet;
	private WidgetSwitchLarge wsFloorSet;
	private WidgetSwitchLarge wsWaterSet;
	private WidgetSwitchLarge wsRadiatorTemp;
	private WidgetSwitchLarge wsFloorTemp;
	private WidgetSwitchLarge wsWaterTemp;
	private WidgetSwitchLarge wsFloorStatus;
	private WidgetSwitchLarge wsFloorSuppInpTemp;
	private WidgetSwitchLarge wsFloorSuppRetTemp;
	private WidgetSwitchLarge wsFloorOutTemp;
	private WidgetSwitchLarge wsFloorRetTemp;
	private WidgetSwitchLarge wsHeatingStatus;
	private WidgetSwitchLarge wsHeatingSuppInpTemp;
	private WidgetSwitchLarge wsHeatingSuppRetTemp;
	private WidgetSwitchLarge wsHeatingOutTemp;
	private WidgetSwitchLarge wsHeatingRetTemp;

	private CommandSender sender;

	private HeatingDialog() {
		super(490, 570, LocaleFactory.getLabels().HeatingDialog_texTitle());
		// 1szy rz¹d
		wsRadiatorSet = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtRadiatorSet());
		wsRadiatorSet.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"radiatorTempSet"));
		wsRadiatorSet.setPopupInputLarge(new PopupInputNumericLarge(
				LocaleFactory.getLabels().HeatingDialog_swtRadiatorSet(),
				"HEAT_MAIN", "command=11;temperature=#;"));
		addWidget(10, 10, wsRadiatorSet);
		wsFloorSet = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtFloorSet());
		wsFloorSet.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"floorTempSet"));
		wsFloorSet.setPopupInputLarge(new PopupInputNumericLarge(
				LocaleFactory.getLabels().HeatingDialog_swtFloorSet(),
				"HEAT_MAIN", "command=12;temperature=#;"));
		addWidget(170, 10, wsFloorSet);
		wsWaterSet = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtWaterSet());
		wsWaterSet.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"waterTempSet"));
		wsWaterSet.setPopupInputLarge(new PopupInputNumericLarge(
				LocaleFactory.getLabels().HeatingDialog_swtWaterSet(),
				"HEAT_MAIN", "command=13;temperature=#;"));
		addWidget(330, 10, wsWaterSet);
		// 2gi rz¹d
		wsRadiatorTemp = new WidgetSwitchLarge("#C");
		wsRadiatorTemp.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"heatingTempInp"));
		wsRadiatorTemp.addReceiver(new ReceiveMapperStatus("HEAT_MAIN",
				"radiatorStatus", "01{on}other{off}"));
		wsRadiatorTemp.setHeight(70);
		addWidget(10, 170, wsRadiatorTemp);
		wsFloorTemp = new WidgetSwitchLarge("#C");
		wsFloorTemp.addReceiver(new ReceiveMapperValue("HEAT_3WAY",
				"valveTempOut"));
		wsFloorTemp.addReceiver(new ReceiveMapperStatus("HEAT_MAIN",
				"floorStatus", "01{on}other{off}"));
		wsFloorTemp.setHeight(70);
		addWidget(170, 170, wsFloorTemp);
		wsWaterTemp = new WidgetSwitchLarge("#C");
		wsWaterTemp.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"waterTempOut"));
		wsWaterTemp.addReceiver(new ReceiveMapperStatus("HEAT_MAIN",
				"waterStatus", "01{on}other{off}"));
		wsWaterTemp.setHeight(70);
		addWidget(330, 170, wsWaterTemp);
		// 3ci rzad
		wsFloorSuppInpTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtOutTemp());
		wsFloorSuppInpTemp.addReceiver(new ReceiveMapperValue("HEAT_MAIN",
				"heatingTempInp"));
		wsFloorSuppInpTemp.setHeight(70);
		addWidget(10, 250, wsFloorSuppInpTemp);
		wsFloorStatus = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtFloorStatus());
		wsFloorStatus.addReceiver(new ReceiveMapperValue("HEAT_3WAY",
				"heatTempSet"));
		wsFloorStatus.addReceiver(new ReceiveMapperStatus("HEAT_3WAY",
				"pumpStatus", "01{on}other{off}"));
		addWidget(170, 250, wsFloorStatus);
		wsFloorOutTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtOutTemp());
		wsFloorOutTemp.addReceiver(new ReceiveMapperValue("HEAT_3WAY",
				"valveTempOut"));
		wsFloorOutTemp.setHeight(70);
		addWidget(330, 250, wsFloorOutTemp);
		wsFloorSuppRetTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtRetTemp());
		wsFloorSuppRetTemp.addReceiver(new ReceiveMapperValue("HEAT_3WAY",
				"valveTempRet"));
		wsFloorSuppRetTemp.setHeight(70);
		addWidget(10, 330, wsFloorSuppRetTemp);
		wsFloorRetTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtRetTemp());
		wsFloorRetTemp.addReceiver(new ReceiveMapperValue("HEAT_3WAY",
				"valveTempRet"));
		wsFloorRetTemp.setHeight(70);
		addWidget(330, 330, wsFloorRetTemp);
		// 4ci rzad
		wsHeatingSuppInpTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtOutTemp());
		wsHeatingSuppInpTemp.addReceiver(new ReceiveMapperValue("HEAT_4WAY",
				"boilerTempInp"));
		wsHeatingSuppInpTemp.setHeight(70);
		addWidget(10, 410, wsHeatingSuppInpTemp);
		wsHeatingStatus = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtHeattingStatus());
		wsHeatingStatus.addReceiver(new ReceiveMapperValue("HEAT_4WAY",
				"heatTempSet"));
		wsHeatingStatus.addReceiver(new ReceiveMapperStatus("HEAT_4WAY",
				"boilerStatus", "01{on}other{off}"));
		addWidget(170, 410, wsHeatingStatus);
		wsHeatingOutTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtOutTemp());
		wsHeatingOutTemp.addReceiver(new ReceiveMapperValue("HEAT_4WAY",
				"valveTempOut"));
		wsHeatingOutTemp.setHeight(70);
		addWidget(330, 410, wsHeatingOutTemp);
		wsHeatingSuppRetTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtRetTemp());
		wsHeatingSuppRetTemp.addReceiver(new ReceiveMapperValue("HEAT_4WAY",
				"boilerTemprRet"));
		wsHeatingSuppRetTemp.setHeight(70);
		addWidget(10, 490, wsHeatingSuppRetTemp);
		wsHeatingRetTemp = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.HeatingDialog_swtRetTemp());
		wsHeatingRetTemp.addReceiver(new ReceiveMapperValue("HEAT_4WAY",
				"valveTempRet"));
		wsHeatingRetTemp.setHeight(70);
		addWidget(330, 490, wsHeatingRetTemp);

		// Button Cancel
		sender = new CommandSender();
		sender.add("HEAT_MAIN", "command=10;");
		sender.add("HEAT_3WAY", "command=10;");
		sender.add("HEAT_4WAY", "command=10;");
	}

	public static void open() {
		if (dialog == null) {
			dialog = new HeatingDialog();
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
