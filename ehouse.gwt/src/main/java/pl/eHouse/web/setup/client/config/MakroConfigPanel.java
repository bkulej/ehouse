package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class MakroConfigPanel extends ConfigPanel {
	
	public final static String supportedType = "00";

	private WidgetText wtTitle;
	private WidgetText wtMakro;
	private WidgetInput wiMakro;
	private WidgetText wtDescription;
	private WidgetInput wiDev1Number;
	private WidgetInput wiDev1Command;
	private WidgetInput wiDev1Param1;
	private WidgetInput wiDev1Param2;
	private WidgetInput wiDev2Number;
	private WidgetInput wiDev2Command;
	private WidgetInput wiDev2Param1;
	private WidgetInput wiDev2Param2;
	private WidgetInput wiDev3Number;
	private WidgetInput wiDev3Command;
	private WidgetInput wiDev3Param1;
	private WidgetInput wiDev3Param2;

	public MakroConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.MakroConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);

		// Makro
		wtMakro = new WidgetText(100, LocaleFactory.getLabels()
				.MakroConfigPanel_texMakro(), WidgetText.ALIGN_LEFT);
		addWidget(20, 50, wtMakro);
		wiMakro = new WidgetInput(100);
		addWidget(120, 50, wiMakro);

		// Description
		wtDescription = new WidgetText(400, LocaleFactory.getLabels()
				.MakroConfigPanel_texDescription(), WidgetText.ALIGN_LEFT);
		addWidget(20, 100, wtDescription);

		// Device 1
		wiDev1Number = new WidgetInput(30);
		addWidget(100, 130, wiDev1Number);
		wiDev1Command = new WidgetInput(30);
		addWidget(140, 130, wiDev1Command);
		wiDev1Param1 = new WidgetInput(30);
		addWidget(180, 130, wiDev1Param1);
		wiDev1Param2 = new WidgetInput(30);
		addWidget(220, 130, wiDev1Param2);

		wiDev2Number = new WidgetInput(30);
		addWidget(100, 170, wiDev2Number);
		wiDev2Command = new WidgetInput(30);
		addWidget(140, 170, wiDev2Command);
		wiDev2Param1 = new WidgetInput(30);
		addWidget(180, 170, wiDev2Param1);
		wiDev2Param2 = new WidgetInput(30);
		addWidget(220, 170, wiDev2Param2);

		wiDev3Number = new WidgetInput(30);
		addWidget(100, 210, wiDev3Number);
		wiDev3Command = new WidgetInput(30);
		addWidget(140, 210, wiDev3Command);
		wiDev3Param1 = new WidgetInput(30);
		addWidget(180, 210, wiDev3Param1);
		wiDev3Param2 = new WidgetInput(30);
		addWidget(220, 210, wiDev3Param2);
	}

	@Override
	public void init(CometEepromGetResponse response) {
		if (isConfigType(response.getType())) {
			wiMakro.setText(response.getValue("makro"));
			wiDev1Number.setText(response.getValue("dev1Number"));
			wiDev1Command.setText(response.getValue("dev1Command"));
			wiDev1Param1.setText(response.getValue("dev1Param1"));
			wiDev1Param2.setText(response.getValue("dev1Param2"));
			wiDev2Number.setText(response.getValue("dev2Number"));
			wiDev2Command.setText(response.getValue("dev2Command"));
			wiDev2Param1.setText(response.getValue("dev2Param1"));
			wiDev2Param2.setText(response.getValue("dev2Param2"));
			wiDev3Number.setText(response.getValue("dev3Number"));
			wiDev3Command.setText(response.getValue("dev3Command"));
			wiDev3Param1.setText(response.getValue("dev3Param1"));
			wiDev3Param2.setText(response.getValue("dev3Param2"));
		} else {
			wiMakro.setText("FFFF");
			wiDev1Number.setText("FF");
			wiDev1Command.setText("FF");
			wiDev1Param1.setText("FF");
			wiDev1Param2.setText("FF");
			wiDev2Number.setText("FF");
			wiDev2Command.setText("FF");
			wiDev2Param1.setText("FF");
			wiDev2Param2.setText("FF");
			wiDev3Number.setText("FF");
			wiDev3Command.setText("FF");
			wiDev3Param1.setText("FF");
			wiDev3Param2.setText("FF");
		}
	}

	@Override
	public String save() throws ValidatorException {
		return "makro="
				+ Validator.checkWordHex(wiMakro.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badMakro())
				+ ";dev1Number="
				+ Validator.checkByteHex(wiDev1Number.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badDevice())
				+ ";dev1Command="
				+ Validator.checkByteHex(wiDev1Command.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badCommand())
				+ ";dev1Param1="
				+ Validator.checkByteHex(wiDev1Param1.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam1())
				+ ";dev1Param2="
				+ Validator.checkByteHex(wiDev1Param2.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam2())
				+ ";dev2Number="
				+ Validator.checkByteHex(wiDev2Number.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badDevice())
				+ ";dev2Command="
				+ Validator.checkByteHex(wiDev2Command.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badCommand())
				+ ";dev2Param1="
				+ Validator.checkByteHex(wiDev2Param1.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam1())
				+ ";dev2Param2="
				+ Validator.checkByteHex(wiDev2Param2.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam2())
				+ ";dev3Number="
				+ Validator.checkByteHex(wiDev3Number.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badDevice())
				+ ";dev3Command="
				+ Validator.checkByteHex(wiDev3Command.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badCommand())
				+ ";dev3Param1="
				+ Validator.checkByteHex(wiDev3Param1.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam1())
				+ ";dev3Param2="
				+ Validator.checkByteHex(wiDev3Param2.getText(), LocaleFactory
						.getMessages().MakroConfigPanel_badParam2())
				+ ";";
	}

}
