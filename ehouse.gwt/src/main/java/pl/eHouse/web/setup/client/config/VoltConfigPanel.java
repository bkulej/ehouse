package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class VoltConfigPanel extends ConfigPanel {
	
	public final static String supportedType = "04";
	
	private WidgetText wtTitle;

	private WidgetText wtLHThreshold;
	private WidgetInput wiLHThreshold;
	private WidgetText wtLHDelay;
	private WidgetInput wiLHDelay;
	private WidgetText wtLHText;
	private WidgetInput wiLHAddress;
	private WidgetInput wiLHCommand;
	private WidgetInput wiLHParam1;
	private WidgetInput wiLHParam2;

	private WidgetText wtHLThreshold;
	private WidgetInput wiHLThreshold;
	private WidgetText wtHLDelay;
	private WidgetInput wiHLDelay;
	private WidgetText wtHLText;
	private WidgetInput wiHLAddress;
	private WidgetInput wiHLCommand;
	private WidgetInput wiHLParam1;
	private WidgetInput wiHLParam2;

	public VoltConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.VoltConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);

		// L2H Threshold
		wtLHThreshold = new WidgetText(250, LocaleFactory.getLabels()
				.VoltConfigPanel_texLHThreshold(), WidgetText.ALIGN_LEFT);
		addWidget(20, 40, wtLHThreshold);
		wiLHThreshold = new WidgetInput(100);
		addWidget(240, 40, wiLHThreshold);
		// L2H Delay
		wtLHDelay = new WidgetText(250, LocaleFactory.getLabels()
				.VoltConfigPanel_texLHDelay(), WidgetText.ALIGN_LEFT);
		addWidget(20, 70, wtLHDelay);
		wiLHDelay = new WidgetInput(100);
		addWidget(240, 70, wiLHDelay);
		// L2H Command
		wtLHText = new WidgetText(400, LocaleFactory.getLabels()
				.VoltConfigPanel_texLHText(), WidgetText.ALIGN_LEFT);
		addWidget(20, 100, wtLHText);
		wiLHAddress = new WidgetInput(100);
		addWidget(30, 130, wiLHAddress);
		wiLHCommand = new WidgetInput(50);
		addWidget(140, 130, wiLHCommand);
		wiLHParam1 = new WidgetInput(50);
		addWidget(200, 130, wiLHParam1);
		wiLHParam2 = new WidgetInput(50);
		addWidget(260, 130, wiLHParam2);

		// H2L Threshold
		wtHLThreshold = new WidgetText(250, LocaleFactory.getLabels()
				.VoltConfigPanel_texHLThreshold(), WidgetText.ALIGN_LEFT);
		addWidget(20, 180, wtHLThreshold);
		wiHLThreshold = new WidgetInput(100);
		addWidget(240, 180, wiHLThreshold);
		// H2L Delay
		wtHLDelay = new WidgetText(250, LocaleFactory.getLabels()
				.VoltConfigPanel_texHLDelay(), WidgetText.ALIGN_LEFT);
		addWidget(20, 210, wtHLDelay);
		wiHLDelay = new WidgetInput(100);
		addWidget(240, 210, wiHLDelay);
		// H2L Command
		wtHLText = new WidgetText(400, LocaleFactory.getLabels()
				.VoltConfigPanel_texHLText(), WidgetText.ALIGN_LEFT);
		addWidget(20, 240, wtHLText);
		wiHLAddress = new WidgetInput(100);
		addWidget(30, 270, wiHLAddress);
		wiHLCommand = new WidgetInput(50);
		addWidget(140, 270, wiHLCommand);
		wiHLParam1 = new WidgetInput(50);
		addWidget(200, 270, wiHLParam1);
		wiHLParam2 = new WidgetInput(50);
		addWidget(260, 270, wiHLParam2);

	}

	@Override
	public void init(CometEepromGetResponse response) {
		if (isConfigType(response.getType())) {
			wiLHThreshold.setText(response.getValue("lhThreshold"));
			wiLHDelay.setText(response.getValue("lhDelay"));
			wiLHAddress.setText(response.getValue("lhAddress"));
			wiLHCommand.setText(response.getValue("lhCommand"));
			wiLHParam1.setText(response.getValue("lhParam1"));
			wiLHParam2.setText(response.getValue("lhParam2"));
			wiHLThreshold.setText(response.getValue("hlThreshold"));
			wiHLDelay.setText(response.getValue("hlDelay"));
			wiHLAddress.setText(response.getValue("hlAddress"));
			wiHLCommand.setText(response.getValue("hlCommand"));
			wiHLParam1.setText(response.getValue("hlParam1"));
			wiHLParam2.setText(response.getValue("hlParam2"));
		} else {
			wiLHThreshold.setText("255");
			wiLHDelay.setText("255");
			wiLHAddress.setText("FFFF");
			wiLHCommand.setText("FF");
			wiLHParam1.setText("FF");
			wiLHParam2.setText("FF");
			wiHLThreshold.setText("255");
			wiHLDelay.setText("255");
			wiHLAddress.setText("FFFF");
			wiHLCommand.setText("FF");
			wiHLParam1.setText("FF");
			wiHLParam2.setText("FF");
		}
	}

	@Override
	public String save() throws ValidatorException {

		return "lhThreshold="
				+ Validator.checkDecByte(wiLHThreshold.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badThreshold())
				+ ";lhDelay="
				+ Validator.checkDecByte(wiLHDelay.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badDelay())
				+ ";lhAddress="
				+ Validator.checkWordHex(wiLHAddress.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badAddress())
				+ ";lhCommand="
				+ Validator.checkByteHex(wiLHCommand.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam1())
				+ ";lhParam1="
				+ Validator.checkByteHex(wiLHParam1.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam1())
				+ ";lhParam2="
				+ Validator.checkByteHex(wiLHParam2.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam2())
				+ ";hlThreshold="
				+ Validator.checkDecByte(wiHLThreshold.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badThreshold())
				+ ";hlDelay="
				+ Validator.checkDecByte(wiHLDelay.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badDelay())
				+ ";hlAddress="
				+ Validator.checkWordHex(wiHLAddress.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badAddress())
				+ ";hlCommand="
				+ Validator.checkByteHex(wiHLCommand.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam1())
				+ ";hlParam1="
				+ Validator.checkByteHex(wiHLParam1.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam1())
				+ ";hlParam2="
				+ Validator.checkByteHex(wiHLParam2.getText(), LocaleFactory
						.getMessages().VoltConfigPanel_badParam2()) + ";";
	}

}
