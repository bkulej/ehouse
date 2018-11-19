package pl.eHouse.web.common.server.execute;

import java.io.File;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.bootloader.Bootloader;
import pl.eHouse.api.bootloader.BootloaderListener;
import pl.eHouse.api.bootloader.IntelHexFile;
import pl.eHouse.api.config.Settings;
import pl.eHouse.api.utils.ConvertUtil;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareSaveResponse;

public class SoftwareSaveExecutor implements BootloaderListener {

	GwtAtmosphereResource resource;
	CometSoftwareSaveMessage message;
	int maxProgres;

	public SoftwareSaveExecutor(CometSoftwareSaveMessage message,
			GwtAtmosphereResource resource) {
		super();
		this.message = message;
		this.resource = resource;
	}

	public static void execute(CometSoftwareSaveMessage message,
			GwtAtmosphereResource resource) {
		try {
			SoftwareSaveExecutor listener = new SoftwareSaveExecutor(
					message, resource);
			Bootloader.load(message.getHardSerial(), ConvertUtil.hexToInt(
					message.getMemorySize(), "Bad memory size"), ConvertUtil
					.hexToInt(message.getPageSize(), "Bad page size"),
					new IntelHexFile(new File(Settings.getUploadDirectory()
							+ message.getFileName())), listener);
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			resource.post(new CometSoftwareSaveResponse(
					message.getHardSerial(), message.getSoftId(), message
							.getSoftType(), 0,
					CometSoftwareSaveResponse.STATUS_END_ERROR));
			e.printStackTrace();
		}
	}

	@Override
	public void start(int maxProgres) {
		this.maxProgres = maxProgres;
		resource.post(new CometSoftwareSaveResponse(message.getHardSerial(),
				message.getSoftId(), message.getSoftType(), 0,
				CometSoftwareSaveResponse.STATUS_START));
	}

	@Override
	public void progres(int curProgres) {
		resource.post(new CometSoftwareSaveResponse(message.getHardSerial(),
				message.getSoftId(), message.getSoftType(),
				((curProgres * 100) / maxProgres),
				CometSoftwareSaveResponse.STATUS_WORKING));
	}

	@Override
	public void end(int result) {
		switch (result) {
		case Bootloader.RESULT_OK:
			resource.post(new CometSoftwareSaveResponse(
					message.getHardSerial(), message.getSoftId(), message
							.getSoftType(), 100,
					CometSoftwareSaveResponse.STATUS_END_OK));
			break;
		default:
			resource.post(new CometSoftwareSaveResponse(
					message.getHardSerial(), message.getSoftId(), message
							.getSoftType(), 100,
					CometSoftwareSaveResponse.STATUS_END_ERROR));
		}
	}
}
