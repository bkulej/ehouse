package pl.eHouse.web.setup.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.web.common.client.comet.CometListener;
import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.locale.LocaleFactory;
import pl.eHouse.web.common.client.utils.ErrorHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Setup implements EntryPoint {
	
	private Logger logger = Logger.getLogger("Setup");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Ustawienie przechwytywania bledow
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable e) {
                logger.log(Level.SEVERE, "Uncaught exception", e);
            }
        });
		// Zakladki w aplikacji
		TabLayoutPanel panSetup = new TabLayoutPanel(25,Unit.PX);
		panSetup.setPixelSize(1200, 600);
		panSetup.getElement().getStyle().setProperty("margin", "auto");
		panSetup.add(new MessageWidget(),LocaleFactory.getLabels().Setup_panMessages());
		panSetup.add(new HistoryWidget(),LocaleFactory.getLabels().Setup_panHistory());
		panSetup.add(new ExploreWidget(),LocaleFactory.getLabels().Setup_panExplore());		
		panSetup.add(new HardwareWidget(),LocaleFactory.getLabels().Setup_panHardware());
		panSetup.add(new ConfigWidget(),LocaleFactory.getLabels().Setup_panConfig());
		panSetup.add(new BootloaderWidget(),LocaleFactory.getLabels().Setup_panBootloader());
		// Panel aplikacji
		LayoutPanel bodyPanel = new LayoutPanel();
		bodyPanel.add(panSetup);
		RootLayoutPanel.get().add(bodyPanel);
		// Inicjacja polaczenia
		CometSender.init();		
		CometListener.setErrorListener(new ErrorHandler());
	}
}
