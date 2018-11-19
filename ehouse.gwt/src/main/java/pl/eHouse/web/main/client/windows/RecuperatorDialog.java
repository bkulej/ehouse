package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.CommandSender;
import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.ReceiveMapperValue;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class RecuperatorDialog extends InfoDialog {

	private static RecuperatorDialog dialog;
	private WidgetSwitchLarge swtSpeed1;
	private WidgetSwitchLarge swtSpeed2;
	private WidgetSwitchLarge swtSpeed3;
	private WidgetSwitchLarge swtGwc;
	private WidgetSwitchLarge swtBypass;
	private WidgetSwitchLarge swtFanIn;
	private WidgetSwitchLarge swtRecIn;
	private WidgetSwitchLarge swtRoomIn;
	private WidgetSwitchLarge swtFanOut;
	private WidgetSwitchLarge swtRecOut;
	private WidgetSwitchLarge swtRoomOut;
	private CommandSender sender;

	private RecuperatorDialog() {
		super(650, 490, LocaleFactory.getLabels().RecuperatorDialog_texTitle());

		swtSpeed1 = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtSpeed1());
		swtSpeed1
				.setSender(new SendMapper("RECUPERATOR",
						"on{command=11;status=00;}off{command=11;status=01;}other{command=10;}"));
		swtSpeed1.addReceiver(new ReceiveMapperStatus("RECUPERATOR",
				"speedStatus", "01{on}other{off}"));
		addWidget(10, 10, swtSpeed1);

		swtSpeed2 = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtSpeed2());
		swtSpeed2
				.setSender(new SendMapper("RECUPERATOR",
						"on{command=11;status=00;}off{command=11;status=02;}other{command=10;}"));
		swtSpeed2.addReceiver(new ReceiveMapperStatus("RECUPERATOR",
				"speedStatus", "02{on}other{off}"));
		addWidget(170, 10, swtSpeed2);

		swtSpeed3 = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtSpeed3());
		swtSpeed3
				.setSender(new SendMapper("RECUPERATOR",
						"on{command=11;status=00;}off{command=11;status=03;}other{command=10;}"));
		swtSpeed3.addReceiver(new ReceiveMapperStatus("RECUPERATOR",
				"speedStatus", "03{on}other{off}"));
		addWidget(330, 10, swtSpeed3);

		swtGwc = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtGwc());
		swtGwc.setSender(new SendMapper("RECUPERATOR",
				"on{command=13;status=00;}off{command=13;status=01;}other{command=10;}"));
		swtGwc.addReceiver(new ReceiveMapperStatus("RECUPERATOR", "gwcStatus",
				"01{on}other{off}"));
		addWidget(490, 10, swtGwc);

		swtBypass = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtBypass());
		swtBypass.setHeight(310);
		swtBypass
				.setSender(new SendMapper("RECUPERATOR",
						"on{command=12;status=00;}off{command=12;status=01;}other{command=10;}"));
		swtBypass.addReceiver(new ReceiveMapperStatus("RECUPERATOR",
				"bypassStatus", "01{on}other{off}"));
		addWidget(330, 170, swtBypass);

		swtFanIn = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtFanIn());
		swtFanIn.addReceiver(new ReceiveMapperValue("RECUPERATOR", "speedFanIn"));
		addWidget(10, 170, swtFanIn);

		swtRecIn = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtRecIn());
		swtRecIn.addReceiver(new ReceiveMapperValue("RECUPERATOR", "tempRecIn"));
		addWidget(170, 170, swtRecIn);

		swtFanOut = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtFanOut());
		swtFanOut.addReceiver(new ReceiveMapperValue("RECUPERATOR",
				"speedFanOut"));
		addWidget(10, 330, swtFanOut);

		swtRecOut = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtRecOut());
		swtRecOut.addReceiver(new ReceiveMapperValue("RECUPERATOR",
				"tempRecOut"));
		addWidget(170, 330, swtRecOut);

		swtRoomIn = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtRoomIn());
		swtRoomIn.addReceiver(new ReceiveMapperValue("RECUPERATOR",
				"tempRoomIn"));
		addWidget(490, 170, swtRoomIn);

		swtRoomOut = new WidgetSwitchLarge(LocaleFactory.getLabels()
				.RecuperatorDialog_swtRoomOut());
		swtRoomOut.addReceiver(new ReceiveMapperValue("RECUPERATOR",
				"tempRoomOut"));
		addWidget(490, 330, swtRoomOut);

		sender = new CommandSender();
		sender.add("RECUPERATOR", "command=10;");
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
			dialog = new RecuperatorDialog();
		}
		dialog.showDialog();
		dialog.center();
		dialog.show();
	}

}
