package pl.eHouse.web.main.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.web.common.client.comet.CometListener;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.command.CommandReceiver;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ErrorHandler;
import pl.eHouse.web.common.client.widgets.large.WidgetIconLarge;
import pl.eHouse.web.main.client.windows.GarageDialog;
import pl.eHouse.web.main.client.windows.HeatingDialog;
import pl.eHouse.web.main.client.windows.IrrigationDialog;
import pl.eHouse.web.main.client.windows.MultimediaDialog;
import pl.eHouse.web.main.client.windows.OutdoorDialog;
import pl.eHouse.web.main.client.windows.RecuperatorDialog;
import pl.eHouse.web.main.client.windows.TemperatureDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

	private Logger logger = Logger.getLogger("EHouse_web");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				logger.log(Level.SEVERE, "Uncaught exception", e);
			}
		});
		// Panel aplikacji
		FlowPanel panMain = new FlowPanel();

		// Ikona podlewania
		WidgetIconLarge icoIrrigation = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoIrrigation(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				IrrigationDialog.open();
			}
		});
		panMain.add(icoIrrigation);

		// Ikona rekuperatora
		WidgetIconLarge icoRecuperator = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoRecuperator(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RecuperatorDialog.open();
			}
		});
		panMain.add(icoRecuperator);

		// Ikona multimediow
		WidgetIconLarge icoMultimedia = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoMultimedia(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MultimediaDialog.open();
			}
		});
		panMain.add(icoMultimedia);

		// Ikona ogrzewania
		WidgetIconLarge icoHeating = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoHeating(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				HeatingDialog.open();
			}
		});
		panMain.add(icoHeating);

		// Ikona zewn¹trz
		WidgetIconLarge icoOutdoor = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoOutdoor(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OutdoorDialog.open();
			}
		});
		panMain.add(icoOutdoor);

		// Ikona zewn¹trz
		WidgetIconLarge icoGarage = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoGarage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GarageDialog.open();
			}
		});
		panMain.add(icoGarage);

		// Ikona Temperatury
		WidgetIconLarge icoTemperature = new WidgetIconLarge(LocaleFactory
				.getLabels().Main_icoTemperature(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TemperatureDialog.open();
			}
		});
		panMain.add(icoTemperature);

		// Panel aplikacji
		ScrollPanel bodyPanel = new ScrollPanel();
		bodyPanel.add(panMain);
		RootLayoutPanel.get().add(bodyPanel);
		// Inicjacja polaczenia
		CometSender.init();
		CometListener.setErrorListener(new ErrorHandler());
		CometListener.setResponseListener(CommandReceiver.getReceiver());
	}
}
