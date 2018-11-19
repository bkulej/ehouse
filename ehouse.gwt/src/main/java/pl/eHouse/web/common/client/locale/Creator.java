package pl.eHouse.web.common.client.locale;

//import com.google.gwt.i18n.tools.I18NSync;

public class Creator {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		String[] labels = {
				"-out",
				"src",
				"pl.eHouse.web.common.client.locale.ILabels"				
		};
		
//		I18NSync.main(labels);
		System.out.println("Labels done");
		
		String[] messages = {
				"-out",
				"src",
				"-createMessages",
				"pl.eHouse.web.common.client.locale.IMessages"				
		};
		
//		I18NSync.main(messages);
		System.out.println("Messages done");

	}

}
