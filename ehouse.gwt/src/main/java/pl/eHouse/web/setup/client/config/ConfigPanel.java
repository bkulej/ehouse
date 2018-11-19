package pl.eHouse.web.setup.client.config;

import java.util.ArrayList;

import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.WidgetPanel;
import pl.eHouse.web.setup.client.ConfigDialog;

public abstract class ConfigPanel extends WidgetPanel {

	public static int x;
	public static int y;
	public final static int width = 400;
	public final static int height = 400;
	public static ConfigDialog parent;

	public static void addPanels(int x, int y, ConfigDialog parent,
			ArrayList<ConfigPanel> panels) {
		ConfigPanel.x = x;
		ConfigPanel.y = y;
		ConfigPanel.parent = parent;
		panels.add(new EmptyConfigPanel());
		panels.add(new MakroConfigPanel());
		panels.add(new InputConfigPanel());
		panels.add(new VoltConfigPanel());
		panels.add(new IrrigationConfigPanel());
		panels.add(new HeatMainConfigPanel());
		panels.add(new RecuperatorConfigPanel());
	}

	protected String type;

	public ConfigPanel(String type) {
		super(width, height);
		this.type = type;
	}
	

	public String getType() {
		return type;
	}

	public boolean isConfigType(String type) {
		return this.type.equals(type);
	}

	public boolean checkAndSet(CometDeviceLoadResponse device) {
		if (isConfigType(device.getType())) {
			if (!isAttached()) {
				parent.addWidget(x, y, this);
			}
			setVisible(true);
			return true;
		} else {
			setVisible(false);
			if (isAttached()) {
				removeFromParent();
			}
			return false;
		}
	}

	public abstract void init(CometEepromGetResponse response);

	public abstract String save() throws ValidatorException;

}
