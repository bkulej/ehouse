package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class IrrigationDialog extends InfoDialog {

	private static IrrigationDialog dialog;
	private WidgetSwitchLarge swtAll;
	private WidgetSwitchLarge swtSec1;
	private WidgetSwitchLarge swtSec2;
	private WidgetSwitchLarge swtSec3;
	private WidgetSwitchLarge swtSec4;
	private WidgetSwitchLarge swtSec5;
	private WidgetSwitchLarge swtSec6;
	private WidgetSwitchLarge swtSec7;
	private WidgetSwitchLarge swtSec8;

	private IrrigationDialog() {
		super(490, 490, LocaleFactory.getLabels().IrrigationDialog_texTitle());
		
		swtAll = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtAll());
		swtAll.setSender(new SendMapper("IRRIGATION","off{command=11;status=01;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtAll.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","00{off}other{on}"));
		addWidget(10, 10, swtAll);
		
		swtSec1 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec1());
		swtSec1.setSender(new SendMapper("IRRIGATION","off{command=11;status=01;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec1.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","01{on}other{off}"));
		addWidget(170, 10, swtSec1);
		
		swtSec2 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec2());
		swtSec2.setSender(new SendMapper("IRRIGATION","off{command=11;status=02;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec2.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","02{on}other{off}"));
		addWidget(330, 10, swtSec2);
		
		swtSec3 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec3());
		swtSec3.setSender(new SendMapper("IRRIGATION","off{command=11;status=03;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec3.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","03{on}other{off}"));
		addWidget(10, 170, swtSec3);
		
		swtSec4 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec4());
		swtSec4.setSender(new SendMapper("IRRIGATION","off{command=11;status=04;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec4.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","04{on}other{off}"));
		addWidget(170, 170, swtSec4);
		
		swtSec5 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec5());
		swtSec5.setSender(new SendMapper("IRRIGATION","off{command=11;status=05;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec5.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","05{on}other{off}"));
		addWidget(330, 170, swtSec5);
		
		swtSec6 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec6());
		swtSec6.setSender(new SendMapper("IRRIGATION","off{command=11;status=06;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec6.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","06{on}other{off}"));
		addWidget(10, 330, swtSec6);
		
		swtSec7 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec7());
		swtSec7.setSender(new SendMapper("IRRIGATION","off{command=11;status=07;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec7.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","07{on}other{off}"));
		addWidget(170, 330, swtSec7);
		
		swtSec8 = new WidgetSwitchLarge(LocaleFactory.getLabels().IrrigationDialog_swtSec8());
		swtSec8.setSender(new SendMapper("IRRIGATION","off{command=11;status=08;delay=00;}on{command=11;status=00;delay=00;}other{command=10;}"));
		swtSec8.addReceiver(new ReceiveMapperStatus("IRRIGATION","status","08{on}other{off}"));
		addWidget(330, 330, swtSec8);
	}

	@Override
	protected void showDialog() {
		swtAll.sendInitial();
	}

	@Override
	protected void hideDialog() {
	}

	public static void open() {
		if (dialog == null) {
			dialog = new IrrigationDialog();
		}
		dialog.showDialog();
		dialog.center();
		dialog.show();
	}

}
