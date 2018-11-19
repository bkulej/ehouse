package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class HeatMainConfigPanel extends ConfigPanel {

	public final static String supportedType = "30";

	private WidgetText wtTitle;
	private WidgetText wtRadiatorTemp;
	private WidgetInput wiRadiatorTemp;
	private WidgetText wtFloorTemp;
	private WidgetInput wiFloorTemp;
	private WidgetText wtWaterTemp;
	private WidgetInput wiWaterTemp;
	private WidgetText wtFloorAddress;
	private WidgetInput wiFloorAddress;
	private WidgetText wtBoilerAddress;
	private WidgetInput wiBoilerAddress;

	public HeatMainConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.MakroConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);

		wtRadiatorTemp = new WidgetText(180, LocaleFactory.getLabels()
				.HeatingConfigPanel_texRadiatorTemp(), WidgetText.ALIGN_LEFT);
		addWidget(30, 50, wtRadiatorTemp);
		wiRadiatorTemp = new WidgetInput(50);
		addWidget(220, 50, wiRadiatorTemp);

		wtFloorTemp = new WidgetText(180, LocaleFactory.getLabels()
				.HeatingConfigPanel_texFloorTemp(), WidgetText.ALIGN_LEFT);
		addWidget(30, 90, wtFloorTemp);
		wiFloorTemp = new WidgetInput(50);
		addWidget(220, 90, wiFloorTemp);

		wtWaterTemp = new WidgetText(180, LocaleFactory.getLabels()
				.HeatingConfigPanel_texWaterTemp(), WidgetText.ALIGN_LEFT);
		addWidget(30, 130, wtWaterTemp);
		wiWaterTemp = new WidgetInput(50);
		addWidget(220, 130, wiWaterTemp);

		wtFloorAddress = new WidgetText(180, LocaleFactory.getLabels()
				.HeatingConfigPanel_texFloorAddress(), WidgetText.ALIGN_LEFT);
		addWidget(30, 170, wtFloorAddress);
		wiFloorAddress = new WidgetInput(100);
		addWidget(220, 170, wiFloorAddress);

		wtBoilerAddress = new WidgetText(180, LocaleFactory.getLabels()
				.HeatingConfigPanel_texBoilerAddress(), WidgetText.ALIGN_LEFT);
		addWidget(30, 210, wtBoilerAddress);
		wiBoilerAddress = new WidgetInput(100);
		addWidget(220, 210, wiBoilerAddress);
	}

	@Override
	public void init(CometEepromGetResponse response) {
		if (isConfigType(response.getType())) {
			wiRadiatorTemp.setText(response.getValue("radiatorTemp"));
			wiFloorTemp.setText(response.getValue("floorTemp"));
			wiWaterTemp.setText(response.getValue("watherTemp"));
			wiFloorAddress.setText(response.getValue("floorAddress"));
			wiBoilerAddress.setText(response.getValue("boilerAddress"));
		} else {
			wiRadiatorTemp.setText("0");
			wiFloorTemp.setText("0");
			wiWaterTemp.setText("0");
			wiFloorAddress.setText("FFFF");
			wiBoilerAddress.setText("FFFF");
		}
	}

	@Override
	public String save() throws ValidatorException {
		return "radiatorTemp="
				+ Validator.checkDecByte(wiRadiatorTemp.getText(),
						LocaleFactory.getMessages()
								.HeatingConfigPanel_badTemperature())
				+ ";floorTemp="
				+ Validator.checkDecByte(wiFloorTemp.getText(),
						LocaleFactory.getMessages()
								.HeatingConfigPanel_badTemperature())
				+ ";watherTemp="
				+ Validator.checkDecByte(wiWaterTemp.getText(),
						LocaleFactory.getMessages()
								.HeatingConfigPanel_badTemperature())
				+ ";floorAddress="
				+ Validator.checkWordHex(wiFloorAddress.getText(),
						LocaleFactory.getMessages()
								.HeatingConfigPanel_badAddress())
				+ ";boilerAddress="
				+ Validator.checkWordHex(wiBoilerAddress.getText(),
						LocaleFactory.getMessages()
								.HeatingConfigPanel_badAddress())
				+ ";";
	}

}
