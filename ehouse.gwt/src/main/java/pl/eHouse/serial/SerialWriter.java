package pl.eHouse.serial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pl.eHouse.api.bootloader.IntelHexException;
import pl.eHouse.api.bootloader.IntelHexFile;

public class SerialWriter {

	private static ArrayList<File> menu = new ArrayList<File>();

	public static void main(String[] args) {
		try {
			showMenu();
			File soft = menu.get(getMenu());
			System.out.println("Wybrano soft:" + soft.getName());
			String serial = getSerial();
			System.out.println("Podano serial:" + serial);
			String name = saveSerial(soft, serial);
			System.out.println("Zapisano w pliku:" + name);
		} catch (Exception e) {
			System.out.println("ERROR:" + e.getMessage());
		}
	}

	private static void showMenu() throws IOException {
		System.out.println("Dostepne oprogramowanie:");
		System.out.println("--------------------------------------");
		File dir = new File(".");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.getName().startsWith("eHouse")
					&& file.getName().endsWith(".hex")) {
				menu.add(file);
				System.out.println((menu.size() - 1) + " - " + file.getName());
			}
		}
		System.out.println("--------------------------------------");
	}
	
	private static int getMenu() throws IOException {
		System.out.print("Wybierz oprogramowanie:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String text = in.readLine();
		int pos = Integer.parseInt(text);
		if((pos < 0) || (pos >= menu.size())) {
			throw new IOException("Niepoprawna opcja.");
		}
		return pos;
	}

	private static String getSerial() throws IOException {
		System.out.print("Podaj serial:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String serial = in.readLine();
		if (serial.length() != 8) {
			throw new IOException("Niepoprawny serial.");
		}
		return serial;
	}
	
	private static String saveSerial(File soft, String serial) throws IntelHexException, FileNotFoundException, IOException {
		IntelHexFile file = new IntelHexFile(soft);            
        file.getLine(0).setData(4, serial.charAt(0));
        file.getLine(0).setData(5, serial.charAt(1));
        file.getLine(0).setData(6, serial.charAt(2));
        file.getLine(0).setData(7, serial.charAt(3));
        file.getLine(0).setData(8, serial.charAt(4));
        file.getLine(0).setData(9, serial.charAt(5));
        file.getLine(0).setData(10, serial.charAt(6));
        file.getLine(0).setData(11, serial.charAt(7));
        String name = soft.getName().replaceFirst("eHouse", serial);
        file.write(name);	
        return name;
	}

}
