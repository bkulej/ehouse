package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetInput;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class IrrigationConfigPanel extends ConfigPanel {

	public final static String supportedType = "33";

	private WidgetText wtTitle;
	private WidgetText wtSection1;
	private WidgetInput wiSection1;
	private WidgetText wtSection2;
	private WidgetInput wiSection2;
	private WidgetText wtSection3;
	private WidgetInput wiSection3;
	private WidgetText wtSection4;
	private WidgetInput wiSection4;
	private WidgetText wtSection5;
	private WidgetInput wiSection5;
	private WidgetText wtSection6;
	private WidgetInput wiSection6;
	private WidgetText wtSection7;
	private WidgetInput wiSection7;
	private WidgetText wtSection8;
	private WidgetInput wiSection8;

	public IrrigationConfigPanel() {
		super(supportedType);
		// Tytul
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texTitle(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);

		// Sekcja 1
		wtSection1 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection1(), WidgetText.ALIGN_LEFT);
		addWidget(40, 50, wtSection1);
		wiSection1 = new WidgetInput(30);
		addWidget(100, 50, wiSection1);
		// Sekcja 2
		wtSection2 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection2(), WidgetText.ALIGN_LEFT);
		addWidget(160, 50, wtSection2);
		wiSection2 = new WidgetInput(30);
		addWidget(220, 50, wiSection2);

		// Sekcja 3
		wtSection3 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection3(), WidgetText.ALIGN_LEFT);
		addWidget(40, 100, wtSection3);
		wiSection3 = new WidgetInput(30);
		addWidget(100, 100, wiSection3);
		// Sekcja 4
		wtSection4 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection4(), WidgetText.ALIGN_LEFT);
		addWidget(160, 100, wtSection4);
		wiSection4 = new WidgetInput(30);
		addWidget(220, 100, wiSection4);

		// Sekcja 5
		wtSection5 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection5(), WidgetText.ALIGN_LEFT);
		addWidget(40, 150, wtSection5);
		wiSection5 = new WidgetInput(30);
		addWidget(100, 150, wiSection5);
		// Sekcja 6
		wtSection6 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection6(), WidgetText.ALIGN_LEFT);
		addWidget(160, 150, wtSection6);
		wiSection6 = new WidgetInput(30);
		addWidget(220, 150, wiSection6);

		// Sekcja 7
		wtSection7 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection7(), WidgetText.ALIGN_LEFT);
		addWidget(40, 200, wtSection7);
		wiSection7 = new WidgetInput(30);
		addWidget(100, 200, wiSection7);
		// Sekcja 8
		wtSection8 = new WidgetText(70, LocaleFactory.getLabels()
				.IrrigationConfigPanel_texSection8(), WidgetText.ALIGN_LEFT);
		addWidget(160, 200, wtSection8);
		wiSection8 = new WidgetInput(30);
		addWidget(220, 200, wiSection8);

	}

	@Override
	public void init(CometEepromGetResponse response) {
		if (isConfigType(response.getType())) {
			wiSection1.setText(response.getValue("section1"));
			wiSection2.setText(response.getValue("section2"));
			wiSection3.setText(response.getValue("section3"));
			wiSection4.setText(response.getValue("section4"));
			wiSection5.setText(response.getValue("section5"));
			wiSection6.setText(response.getValue("section6"));
			wiSection7.setText(response.getValue("section7"));
			wiSection8.setText(response.getValue("section8"));
		} else {
			wiSection1.setText("0");
			wiSection2.setText("0");
			wiSection3.setText("0");
			wiSection4.setText("0");
			wiSection5.setText("0");
			wiSection6.setText("0");
			wiSection7.setText("0");
			wiSection8.setText("0");
		}
	}

	@Override
	public String save() throws ValidatorException {
		return "section1="
				+ Validator.checkDecByte(wiSection1.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section2="
				+ Validator.checkDecByte(wiSection2.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section3="
				+ Validator.checkDecByte(wiSection3.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section4="
				+ Validator.checkDecByte(wiSection4.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section5="
				+ Validator.checkDecByte(wiSection5.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section6="
				+ Validator.checkDecByte(wiSection6.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section7="
				+ Validator.checkDecByte(wiSection7.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";section8="
				+ Validator.checkDecByte(wiSection8.getText(), LocaleFactory
						.getMessages().IrrigationConfigPanel_badTimeSection())
				+ ";";
	}
}
