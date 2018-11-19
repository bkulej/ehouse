package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.CommandSender;
import pl.eHouse.web.common.client.command.ReceiveMapperValue;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class TemperatureDialog extends InfoDialog {

	private static TemperatureDialog dialog;
	private WidgetSwitchLarge wsTempSalon;
	private WidgetSwitchLarge wsTempHall;
	private WidgetSwitchLarge wsTempBathS;
	private WidgetSwitchLarge wsTempBathB;
	private WidgetSwitchLarge wsTempRoom1;
	private WidgetSwitchLarge wsTempRoom2;
	private WidgetSwitchLarge wsTempRoom3;
	private WidgetSwitchLarge wsTempRoom4;

	private CommandSender sender;

	private TemperatureDialog() {
		super(650, 330, LocaleFactory.getLabels().TemperatureDialog_texTemp());

		wsTempSalon = new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempSalon());
		wsTempSalon.addReceiver(new ReceiveMapperValue("SALON_TEMP_SEN","value"));
		addWidget(10, 10, wsTempSalon);

		wsTempHall = new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempHall());
		wsTempHall.addReceiver(new ReceiveMapperValue("HALL_TEMP_SEN", "value"));
		addWidget(170, 10, wsTempHall);
		
		wsTempBathS = new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempBathS());
		wsTempBathS.addReceiver(new ReceiveMapperValue("BATHS_TEMP_SEN", "value"));
		addWidget(330, 10, wsTempBathS);
		
		wsTempBathB = new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempBathB());
		wsTempBathB.addReceiver(new ReceiveMapperValue("BATHB_TEMP_SEN", "value"));
		addWidget(490, 10, wsTempBathB);
		
		wsTempRoom1= new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempRoom1());
		wsTempRoom1.addReceiver(new ReceiveMapperValue("ROOM1_TEMP_SEN", "value"));
		addWidget(10, 170, wsTempRoom1);
		
		wsTempRoom2= new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempRoom2());
		wsTempRoom2.addReceiver(new ReceiveMapperValue("ROOM2_TEMP_SEN", "value"));
		addWidget(170, 170, wsTempRoom2);
		
		wsTempRoom3= new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempRoom3());
		wsTempRoom3.addReceiver(new ReceiveMapperValue("ROOM3_TEMP_SEN", "value"));
		addWidget(330, 170, wsTempRoom3);
		
		wsTempRoom4= new WidgetSwitchLarge(LocaleFactory.getLabels().TemperatureDialog_swtTempRoom4());
		wsTempRoom4.addReceiver(new ReceiveMapperValue("ROOM4_TEMP_SEN", "value"));
		addWidget(490, 170, wsTempRoom4);

		sender = new CommandSender();
		sender.add("SALON_TEMP_SEN", "command=10;");
		sender.add("HALL_TEMP_SEN", "command=10;");
		sender.add("BATHS_TEMP_SEN", "command=10;");
		sender.add("BATHB_TEMP_SEN", "command=10;");
		sender.add("ROOM1_TEMP_SEN", "command=10;");
		sender.add("ROOM2_TEMP_SEN", "command=10;");
		sender.add("ROOM3_TEMP_SEN", "command=10;");
		sender.add("ROOM4_TEMP_SEN", "command=10;");

	}

	@Override
	protected void showDialog() {
		dialog.sender.start();

	}

	@Override
	protected void hideDialog() {
		dialog.sender.stop();
	}

	public static void open() {
		if (dialog == null) {
			dialog = new TemperatureDialog();
		}
		dialog.showDialog();
		dialog.center();
		dialog.show();
	}

}
