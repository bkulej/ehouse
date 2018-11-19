package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class InputConfigPanel extends ConfigPanel {

	public final static String supportedType = "05";

	private WidgetText wtTitle;
	private WidgetText wtDelay;
	private WidgetInput wiDelay;
	private WidgetText wtAction1;
	private WidgetInput wiAddressAction1;
	private WidgetInput wiCommandAction1;
	private WidgetInput wiParam1Action1;
	private WidgetInput wiParam2Action1;
	private WidgetText wtAction2;
	private WidgetInput wiAddressAction2;
	private WidgetInput wiCommandAction2;
	private WidgetInput wiParam1Action2;
	private WidgetInput wiParam2Action2;

	public InputConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.InputConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);
		// Delay
		wtDelay = new WidgetText(170, LocaleFactory.getLabels()
				.InputConfigPanel_texDelay(), WidgetText.ALIGN_LEFT);
		addWidget(20, 40, wtDelay);
		wiDelay = new WidgetInput(100);
		addWidget(200, 40, wiDelay);
		// Pressing
		wtAction1 = new WidgetText(350, 45, LocaleFactory.getLabels()
				.InputConfigPanel_texAction1(), WidgetText.ALIGN_LEFT);
		addWidget(20, 90, wtAction1);
		wiAddressAction1 = new WidgetInput(100);
		addWidget(30, 140, wiAddressAction1);
		wiCommandAction1 = new WidgetInput(50);
		addWidget(140, 140, wiCommandAction1);
		wiParam1Action1 = new WidgetInput(50);
		addWidget(200, 140, wiParam1Action1);
		wiParam2Action1 = new WidgetInput(50);
		addWidget(260, 140, wiParam2Action1);
		// Short releasing
		wtAction2 = new WidgetText(350, 45, LocaleFactory.getLabels()
				.InputConfigPanel_texAction2(), WidgetText.ALIGN_LEFT);
		addWidget(20, 200, wtAction2);
		wiAddressAction2 = new WidgetInput(100);
		addWidget(30, 250, wiAddressAction2);
		wiCommandAction2 = new WidgetInput(50);
		addWidget(140, 250, wiCommandAction2);
		wiParam1Action2 = new WidgetInput(50);
		addWidget(200, 250, wiParam1Action2);
		wiParam2Action2 = new WidgetInput(50);
		addWidget(260, 250, wiParam2Action2);
	}

	@Override
	public void init(CometEepromGetResponse response) {
		if (isConfigType(response.getType())) {
			wiDelay.setText(response.getValue("delay"));
			wiAddressAction1.setText(response.getValue("addressAction1"));
			wiCommandAction1.setText(response.getValue("commandAction1"));
			wiParam1Action1.setText(response.getValue("param1Action1"));
			wiParam2Action1.setText(response.getValue("param2Action1"));
			wiAddressAction2.setText(response.getValue("addressAction2"));
			wiCommandAction2.setText(response.getValue("commandAction2"));
			wiParam1Action2.setText(response.getValue("param1Action2"));
			wiParam2Action2.setText(response.getValue("param2Action2"));
		} else {
			wiDelay.setText("65535");
			wiAddressAction1.setText("FFFF");
			wiCommandAction1.setText("FF");
			wiParam1Action1.setText("FF");
			wiParam2Action1.setText("FF");
			wiAddressAction2.setText("FFFF");
			wiCommandAction2.setText("FF");
			wiParam1Action2.setText("FF");
			wiParam2Action2.setText("FF");
		}
	}

	@Override
	public String save() throws ValidatorException {
		return "delay="
				+ Validator.checkDecWord(wiDelay.getText(), LocaleFactory
						.getMessages().InputConfigPanel_badDelay())
				+ ";addressAction1="
				+ Validator.checkWordHex(wiAddressAction1.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badAddress())
				+ ";commandAction1="
				+ Validator.checkByteHex(wiCommandAction1.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badCommand())
				+ ";param1Action1="
				+ Validator.checkByteHex(wiParam1Action1.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badParam1())
				+ ";param2Action1="
				+ Validator.checkByteHex(wiParam2Action1.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badParam2())
				+ ";addressAction2="
				+ Validator.checkWordHex(wiAddressAction2.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badAddress())
				+ ";commandAction2="
				+ Validator.checkByteHex(wiCommandAction2.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badCommand())
				+ ";param1Action2="
				+ Validator.checkByteHex(wiParam1Action2.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badParam1())
				+ ";param2Action2="
				+ Validator.checkByteHex(wiParam2Action2.getText(),
						LocaleFactory.getMessages()
								.InputConfigPanel_badParam2()) + ";";
	}
}
