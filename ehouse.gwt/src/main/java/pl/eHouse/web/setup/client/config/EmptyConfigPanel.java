package pl.eHouse.web.setup.client.config;

import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetText;

public class EmptyConfigPanel extends ConfigPanel {

	public final static String supportedType = "FF";
	public final static String supportedDevice = "0F";

	private WidgetText wtTitle;

	public EmptyConfigPanel() {
		super(supportedType);
		wtTitle = new WidgetText(400, LocaleFactory.getLabels()
				.EmptyConfigPanel_emptyConfig(), WidgetText.ALIGN_CENTER);
		addWidget(0, 0, wtTitle);
	}
	
	@Override
	public void init(CometEepromGetResponse response) {
	}
	
	@Override
	public String save() throws ValidatorException {
		return "";
	}
	
}
