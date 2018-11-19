package pl.eHouse.web.main.client.windows;

import pl.eHouse.web.common.client.command.ReceiveMapperStatus;
import pl.eHouse.web.common.client.command.SendMapper;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.large.WidgetSwitchLarge;
import pl.eHouse.web.main.client.InfoDialog;

public class MultimediaDialog extends InfoDialog {

	private static MultimediaDialog dialog;
	private WidgetSwitchLarge swtInternet;
	private WidgetSwitchLarge swtSatellite;
	private WidgetSwitchLarge swtOther;

	private MultimediaDialog() {
		super(330, 490, LocaleFactory.getLabels().MultimediaDialog_texTitle());
		
		swtInternet = new WidgetSwitchLarge(LocaleFactory.getLabels().MultimediaDialog_swtInternet());
		swtInternet.setWidth(310);
		swtInternet.setSender(new SendMapper("MULTI_INTERNET","on{command=11;status=00;delay=05;}other{command=10;}"));
		swtInternet.addReceiver(new ReceiveMapperStatus("MULTI_INTERNET","status","01{on}00{off}"));
		addWidget(10, 10, swtInternet);
		
		swtSatellite = new WidgetSwitchLarge(LocaleFactory.getLabels().MultimediaDialog_swtSatellite());
		swtSatellite.setWidth(310);
		swtSatellite.setSender(new SendMapper("MULTI_SATELLITE","on{command=11;status=00;delay=00;}off{command=12;status=00;delay=00;}other{command=10;}"));
		swtSatellite.addReceiver(new ReceiveMapperStatus("MULTI_SATELLITE","status","01{on}00{off}"));
		addWidget(10, 170, swtSatellite);
		
		swtOther = new WidgetSwitchLarge(LocaleFactory.getLabels().MultimediaDialog_swtOther());
		swtOther.setWidth(310);
		swtOther.setSender(new SendMapper("MULTI_OTHER","on{command=11;status=00;delay=00;}off{command=12;status=00;delay=00;}other{command=10;}"));
		swtOther.addReceiver(new ReceiveMapperStatus("MULTI_OTHER","status","01{on}00{off}"));
		addWidget(10, 330, swtOther);
	}

	@Override
	protected void showDialog() {
		swtInternet.sendInitial();
		swtSatellite.sendInitial();
		swtOther.sendInitial();
	}

	@Override
	protected void hideDialog() {
	}

	public static void open() {
		if (dialog == null) {
			dialog = new MultimediaDialog();
		}
		dialog.showDialog();
		dialog.center();
		dialog.show();
	}

}
