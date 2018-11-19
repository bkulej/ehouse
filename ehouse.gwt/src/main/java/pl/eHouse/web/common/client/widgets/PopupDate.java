package pl.eHouse.web.common.client.widgets;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class PopupDate extends PopupPanel {

	private static PopupDate dialog = new PopupDate();

	private WidgetDate input;
	private DatePicker picker;

	private PopupDate() {
		// Inicjacja okna
		super();
		setAnimationEnabled(true);
		setGlassEnabled(true);
		picker = new DatePicker();
		picker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (input != null) {
					input.setDate(event.getValue());
					hide();
				}
			}
		});
		add(picker);
		hide();
	}

	public static void close() {
		dialog.hide();
	}

	public static void getDate(WidgetDate input, int x, int y) {
		dialog.input = input;
		dialog.picker.setValue(input.getDate());
		dialog.setPopupPosition(x, y);;
		dialog.show();
	}

}
