package pl.eHouse.web.setup.client;

import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ClientConst;
import pl.eHouse.web.common.client.utils.Validator;
import pl.eHouse.web.common.client.utils.ValidatorException;
import pl.eHouse.web.common.client.widgets.PopupMessage;
import pl.eHouse.web.common.client.widgets.WidgetArea;
import pl.eHouse.web.common.client.widgets.WidgetButton;
import pl.eHouse.web.common.client.widgets.WidgetDialog;
import pl.eHouse.web.common.client.widgets.WidgetText;
import pl.eHouse.web.common.client.widgets.WidgetUpload;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;

public class UploadDialog extends WidgetDialog {

	private static UploadDialog dialog;

	private FormPanel form;
	private Hidden whType;
	private WidgetUpload wuUpload;
	private WidgetText wtDescription;
	private WidgetArea waDescription;
	private WidgetButton wbUpload;
	private WidgetButton wbClose;

	private UploadDialog() {
		// Inicjacja okna
		super(230, 210);
		// Hiden type
		whType = new Hidden(ClientConst.UPLOAD_FIELD_TYPE);
		addWidget(whType);
		// Label serial
		wuUpload = new WidgetUpload(ClientConst.UPLOAD_FIELD_FILE);
		wuUpload.setWidth(210);
		addWidget(10, 10, wuUpload);
		// Name text
		wtDescription = new WidgetText(170, LocaleFactory.getLabels()
				.UploadDialog_texDescription(), WidgetText.ALIGN_LEFT);
		addWidget(10, 40, wtDescription);
		// Name area
		waDescription = new WidgetArea(210, 100);
		waDescription.setName(ClientConst.UPLOAD_FIELD_DESC);
		addWidget(10, 65, waDescription);
		// Button Set
		wbUpload = new WidgetButton(LocaleFactory.getLabels()
				.UploadDialog_butUpload(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					Validator.checkString(wuUpload.getFilename(), LocaleFactory
							.getMessages().UploadDialog_emptyFile());
					Validator.checkString(waDescription.getText(),
							LocaleFactory.getMessages()
									.UploadDialog_emptyDescription());
					form.submit();
				} catch (ValidatorException e) {
					PopupMessage.showWarning(e.getMessage());
				}
			}
		});
		addWidget(50, 180, wbUpload);
		// Button Cancel
		wbClose = new WidgetButton(LocaleFactory.getLabels()
				.UploadDialog_butClose(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addWidget(140, 180, wbClose);
		// Forma
		form = new FormPanel();
		form.setWidget(getPanel());
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setAction(ClientConst.UPLOAD_ACTION);
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				hide();
			}
		});
		// Inicjacja
		setWidget(form);
	}

	public static void open(String type) {
		if (dialog == null) {
			dialog = new UploadDialog();
		}
		dialog.whType.setValue(type);
		dialog.center();
		dialog.show();
	}
}
