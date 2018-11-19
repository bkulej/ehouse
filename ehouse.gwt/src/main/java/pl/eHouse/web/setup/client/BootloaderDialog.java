package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometBootFindResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveResponse;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetText;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class BootloaderDialog extends WidgetDialog implements CometInterface {

	private static BootloaderDialog dialog;

	private WidgetText wtHardware;
	private WidgetText wtSoftware;
	private WidgetText wtStatus;
	private WidgetButton wbSave;
	private WidgetButton wbClose;

	private CometBootFindResponse hardware;
	private CometSoftwareLoadResponse software;

	private BootloaderDialog() {
		super(230, 150);
		// Hardware
		wtHardware = new WidgetText(210, LocaleFactory.getLabels()
				.BootloaderDialog_texHardware(), WidgetText.ALIGN_LEFT);
		addWidget(10, 10, wtHardware);
		// Software
		wtSoftware = new WidgetText(210, LocaleFactory.getLabels()
				.BootloaderDialog_texSoftware(), WidgetText.ALIGN_LEFT);
		addWidget(10, 40, wtSoftware);
		// Status
		wtStatus = new WidgetText(210, LocaleFactory.getMessages()
				.BootloaderDialog_statusReady(), WidgetText.ALIGN_CENTER);
		addWidget(10, 70, wtStatus);
		// Button Set
		wbSave = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderDialog_butSave(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});
		addWidget(50, 110, wbSave);
		// Button Cancel
		wbClose = new WidgetButton(LocaleFactory.getLabels()
				.BootloaderDialog_butClose(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addWidget(140, 110, wbClose);
	}

	public static void open(CometBootFindResponse hardware,
			CometSoftwareLoadResponse software) {
		if (dialog == null) {
			dialog = new BootloaderDialog();
		}
		dialog.hardware = hardware;
		dialog.software = software;
		dialog.wtHardware.setText(LocaleFactory.getLabels()
				.BootloaderDialog_texHardware() + hardware.getHardwareType());
		dialog.wtSoftware.setText(LocaleFactory.getLabels()
				.BootloaderDialog_texSoftware() + software.getDescription());
		dialog.wtStatus.setText(LocaleFactory.getMessages()
				.BootloaderDialog_statusReady());
		dialog.center();
		dialog.show();
	}

	private void save() {
		wtStatus.setText(LocaleFactory.getMessages()
				.BootloaderDialog_statusStarted());
		wbSave.setEnabled(false);
		wbClose.setEnabled(false);
		CometSoftwareSaveMessage message = new CometSoftwareSaveMessage(
				hardware.getSerial(), hardware.getMemorySize(),
				hardware.getPageSize(), software.getId(), software.getType());
		CometSender.send(message, this);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometSoftwareSaveResponse) {
			CometSoftwareSaveResponse response = (CometSoftwareSaveResponse) message;
			switch (response.getStatus()) {
			case CometSoftwareSaveResponse.STATUS_START:
			case CometSoftwareSaveResponse.STATUS_WORKING:
				wtStatus.setText(Integer.toString(response.getProgres()) + "%");
				break;
			case CometSoftwareSaveResponse.STATUS_END_OK:
				wtStatus.setText(Integer.toString(response.getProgres())
						+ "% OK");
				wbSave.setEnabled(true);
				wbClose.setEnabled(true);
				break;
			case CometSoftwareSaveResponse.STATUS_END_ERROR:
				wtStatus.setText(Integer.toString(response.getProgres())
						+ "% ERROR");
				wbSave.setEnabled(true);
				wbClose.setEnabled(true);
				break;
			}
		}
	}
}
