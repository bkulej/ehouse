package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class RecuperatorConfigPanel extends ConfigPanel {

	public final static String supportedType = "34";

	private WidgetText wtTitle;
	private WidgetText wtSpeed1;
	private WidgetInput wiSpeed1FanIn;
	private WidgetInput wiSpeed1FanOut;
	private WidgetText wtSpeed2;
	private WidgetInput wiSpeed2FanIn;
	private WidgetInput wiSpeed2FanOut;
	private WidgetText wtSpeed3;
	private WidgetInput wiSpeed3FanIn;
	private WidgetInput wiSpeed3FanOut;

	public RecuperatorConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.RecuperatorConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);

		// Speed 1
		wtSpeed1 = new WidgetText(170, LocaleFactory.getLabels()
				.RecuperatorConfigPanel_texSpeed1(), WidgetText.ALIGN_LEFT);
		addWidget(40, 50, wtSpeed1);
		wiSpeed1FanIn = new WidgetInput(30);
		addWidget(220, 50, wiSpeed1FanIn);
		wiSpeed1FanOut = new WidgetInput(30);
		addWidget(270, 50, wiSpeed1FanOut);

		// Speed 2
		wtSpeed2 = new WidgetText(170, LocaleFactory.getLabels()
				.RecuperatorConfigPanel_texSpeed2(), WidgetText.ALIGN_LEFT);
		addWidget(40, 90, wtSpeed2);
		wiSpeed2FanIn = new WidgetInput(30);
		addWidget(220, 90, wiSpeed2FanIn);
		wiSpeed2FanOut = new WidgetInput(30);
		addWidget(270, 90, wiSpeed2FanOut);

		// Speed 3
		wtSpeed3 = new WidgetText(170, LocaleFactory.getLabels()
				.RecuperatorConfigPanel_texSpeed3(), WidgetText.ALIGN_LEFT);
		addWidget(40, 130, wtSpeed3);
		wiSpeed3FanIn = new WidgetInput(30);
		addWidget(220, 130, wiSpeed3FanIn);
		wiSpeed3FanOut = new WidgetInput(30);
		addWidget(270, 130, wiSpeed3FanOut);
	}

	@Override
	public void init(CometEepromGetResponse response) {

		if (isConfigType(response.getType())) {
			wiSpeed1FanIn.setText(response.getValue("speed1FanIn"));
			wiSpeed1FanOut.setText(response.getValue("speed1FanOut"));
			wiSpeed2FanIn.setText(response.getValue("speed2FanIn"));
			wiSpeed2FanOut.setText(response.getValue("speed2FanOut"));
			wiSpeed3FanIn.setText(response.getValue("speed3FanIn"));
			wiSpeed3FanOut.setText(response.getValue("speed3FanOut"));
		} else {
			wiSpeed1FanIn.setText("0");
			wiSpeed1FanOut.setText("0");
			wiSpeed2FanIn.setText("0");
			wiSpeed2FanOut.setText("0");
			wiSpeed3FanIn.setText("0");
			wiSpeed3FanOut.setText("0");
		}

	}

	@Override
	public String save() throws ValidatorException {

		return "speed1FanIn="
				+ Validator.checkDecByte(wiSpeed1FanIn.getText(), LocaleFactory
						.getMessages().RecuperatorConfigPanel_badValue())
				+ ";speed1FanOut="
				+ Validator.checkDecByte(wiSpeed1FanOut.getText(),
						LocaleFactory.getMessages()
								.RecuperatorConfigPanel_badValue())
				+ ";speed2FanIn="
				+ Validator.checkDecByte(wiSpeed2FanIn.getText(), LocaleFactory
						.getMessages().RecuperatorConfigPanel_badValue())
				+ ";speed2FanOut="
				+ Validator.checkDecByte(wiSpeed2FanOut.getText(),
						LocaleFactory.getMessages()
								.RecuperatorConfigPanel_badValue())
				+ ";speed3FanIn="
				+ Validator.checkDecByte(wiSpeed3FanIn.getText(), LocaleFactory
						.getMessages().RecuperatorConfigPanel_badValue())
				+ ";speed3FanOut="
				+ Validator.checkDecByte(wiSpeed3FanOut.getText(),
						LocaleFactory.getMessages()
								.RecuperatorConfigPanel_badValue()) + ";";

	}
}
