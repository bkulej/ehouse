/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import pl.eHouse.api.bootloader.Software;
import pl.eHouse.api.cron.CronTask;
import pl.eHouse.api.hardware.Device;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.hardware.HardwareException;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 * 
 * @author Bartek
 */
public class Config {

	private static Config instance;
	private final static String sourceDesc = "pl/eHouse/api/config/description.xml";
	private Document documentDesc;
	private ConcurrentHashMap<Integer, Hardware> hardAddress = new ConcurrentHashMap<Integer, Hardware>();
	private ConcurrentHashMap<String, Hardware> hardSerial = new ConcurrentHashMap<String, Hardware>();
	private ConcurrentHashMap<String, Device> devNames = new ConcurrentHashMap<String, Device>();
	private ConcurrentHashMap<String, Software> softwares = new ConcurrentHashMap<String, Software>();
	private ArrayList<CronTask> tasks = new ArrayList<CronTask>();

	/**
	 * Konstruktor
	 * 
	 * @throws ConfigException
	 */
	private Config() throws ConfigException {
		try {
			// Plik opisujacy urzadzenia
			documentDesc = new SAXBuilder().build(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(sourceDesc));
			// Wczytanie skonfigurowanych urzadzen
			read();
		} catch (Exception ex) {
			Logger.getLogger(Config.class.getName())
					.log(Level.SEVERE, null, ex);
			throw new ConfigException(ex.getMessage());
		}
	}

	/**
	 * Pobranie instancji
	 * 
	 * @return
	 * @throws ConfigException
	 */
	public static synchronized Config getInstance() throws ConfigException {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	/**
	 * Pobranie calego oprogramowania
	 * 
	 * @return
	 */
	public synchronized Collection<Software> getSoftwares() {
		return softwares.values();
	}

	/**
	 * Pobranie info o oprogramowanie
	 * 
	 * @param id
	 * @return
	 */
	public synchronized Software getSoftware(String id) {
		return softwares.get(id);
	}

	/**
	 * Usuniêcie oprogramowania
	 * 
	 * @param id
	 * @throws ConfigException
	 */
	public synchronized void removeSoftware(String id) throws ConfigException {
		softwares.remove(id);
		save();
	}

	/**
	 * Pobranie taskow
	 * 
	 * @return
	 */
	public synchronized List<CronTask> getCronTasks() {
		return tasks;
	}

	/**
	 * Pobranie wszystkich hardware
	 * 
	 * @return
	 */
	public synchronized Collection<Hardware> getHardwares() {
		return hardAddress.values();
	}

	/**
	 * Pobranie hardware po adresie
	 * 
	 * @param address
	 * @return
	 */
	public synchronized Hardware getHardware(Address address) {
		return hardAddress.get(address.getInt());
	}

	/**
	 * Pobranie hardware po adresie
	 * 
	 * @param address
	 * @return
	 */
	public synchronized Hardware getHardware(String serial) {
		return hardSerial.get(serial);
	}

	/**
	 * Pobranie device po adresie
	 * 
	 * @param address
	 * @return
	 */
	public synchronized Device getDevice(Address address) {
		Hardware hardware = hardAddress.get(address.getHardwareNumber());
		if (hardware == null) {
			return null;
		}
		return hardware.getDevice(address.getDeviceNumber());
	}

	/**
	 * Pobranie device po name
	 * 
	 * @param address
	 * @return
	 */
	public synchronized Device getDevice(String name) {
		return devNames.get(name);
	}

	/**
	 * Metoda zwraca liste wolnych adresow
	 * 
	 * @return
	 */
	public synchronized List<Address> getFreeAddresses() {
		ArrayList<Address> result = new ArrayList<Address>();
		for (int i = 0x0110; i <= 0xfff0; i += 0x10) {
			if ((i & 0x00FF) != 00) {
				Address address = new Address(i);
				if (!hardAddress.containsKey(address.getInt())) {
					result.add(address);
				}
			}
		}
		return result;
	}

	/**
	 * Zapisanie software
	 * 
	 * @param software
	 * @throws ConfigException
	 */
	public synchronized void save(Software software) throws ConfigException {
		softwares.put(software.getId(), software);
		save();
	}

	/**
	 * Zapisanie hardware
	 * 
	 * @param hardware
	 * @throws HardwareException
	 * @throws ConfigException
	 */
	public synchronized void save(Hardware hardware) throws HardwareException,
			ConfigException {
		// Czy jest address
		if (hardware.getAddress() == null) {
			throw new HardwareException("Address is empty");
		}
		// Odczytanie danych hardware
		read(hardware);
		// Usuniêcie deviców
		for (Device device : hardware.getDevices()) {
			if (device.getName() != null) {
				devNames.remove(device.getName());
			}
		}
		// Usuniecie hardwares z listy
		for (Hardware iHardware : hardAddress.values()) {
			if (iHardware.equals(hardware)) {
				hardAddress.remove(iHardware.getAddress().getInt());
			}
		}
		// Usuniecie hardwares z listy
		for (Hardware iHardware : hardSerial.values()) {
			if (iHardware.equals(hardware)) {
				hardSerial.remove(iHardware.getSerial());
			}
		}
		// Czy adres pusty
		if (!hardware.getAddress().equals(Address.ADDRESS_EMPTY)) {
			// Usuniecie adresu z listy
			hardAddress.remove(hardware.getAddress().getInt());
			// Wstawienie hardware
			hardAddress.put(hardware.getAddress().getInt(), hardware);
			hardSerial.put(hardware.getSerial(), hardware);
			// Wstawienie device
			for (Device device : hardware.getDevices()) {
				if (device.getName() != null) {
					devNames.put(device.getName(), device);
				}
			}
		}
		save();
	}

	/*
	 * Odczytanie device dla hardware
	 */
	@SuppressWarnings("unchecked")
	private void read(Hardware hardware) throws ConfigException {
		// Znalezienie typu hardware w pliku opisu
		Element xHardware = null;
		for (Element eHardware : (List<Element>) documentDesc.getRootElement()
				.getChildren("hardware")) {
			// Czy odpowiedni typ hardware i software
			if (hardware.getHardwareType().equals(
					eHardware.getAttributeValue("hardwareType"))
					&& hardware.getSoftwareType().equals(
							eHardware.getAttributeValue("softwareType"))) {
				xHardware = eHardware;
				break;
			}
		}
		if (xHardware == null) {
			throw new ConfigException("No hardware configurationdt");
		}
		// Odczytanie deviców je¿eli ich brak
		if (hardware.getDevices() == null) {
			// Odczyt device dla hardware
			for (Element eDevice : (List<Element>) xHardware
					.getChildren("device")) {
				try {
					hardware.addDevice(new Device(hardware, eDevice
							.getAttributeValue("number"), eDevice
							.getAttributeValue("type"), "", ""));
				} catch (ConvertException e) {
					throw new ConfigException("Config file: " + e.getMessage());
				}
			}
		}
	}

	/*
	 * Zapisanie mapowania
	 */
	private void save() throws ConfigException {
		// Utworzenie nowego dokumentu
		Element root = new Element("ehouse");
		Document documentConf = new Document(root);
		// Dodanie czesci hardware
		root.addContent(createHardwareElements(new Element("hardware")));
		// Dodanie czesci software
		root.addContent(createSoftwareElements(new Element("software")));
		// Dodanie czesci crona
		root.addContent(createCronElements(new Element("cron")));
		// Zapisanie do pliku
		try {
			FileWriter writer = new FileWriter(Settings.getConfigFilePath());
			XMLOutputter serializer = new XMLOutputter();
			serializer.setFormat(Format.getPrettyFormat());
			serializer.output(documentConf, writer);
		} catch (IOException e) {
			throw new ConfigException(e.getMessage());
		} catch (SettingsException e) {
			throw new ConfigException(e.getMessage());
		}
	}

	/*
	 * Utworzenie dokumentu z hardware
	 */
	private Element createHardwareElements(Element part) {
		// Utworzenie XMLa
		for (Hardware hardware : hardAddress.values()) {
			// Utworzenie hardware
			Element eHardware = new Element("hardware-" + hardware.getId());
			eHardware.setAttribute("serial", hardware.getSerial());
			eHardware.setAttribute("softwareType", hardware.getSoftwareType());
			eHardware.setAttribute("hardwareType", hardware.getHardwareType());
			eHardware.setAttribute("address", hardware.getAddress().toString());
			eHardware.setAttribute("description", hardware.getDescription());
			// Utworzenie devices
			for (Device device : hardware.getDevices()) {
				Element eDevice = new Element("device");
				eDevice.setAttribute("number",
						ConvertUtil.byteToHex(device.getNumber()));
				eDevice.setAttribute("type",
						ConvertUtil.byteToHex(device.getType()));
				eDevice.setAttribute("name", device.getName());
				eDevice.setAttribute("description", device.getDescription());
				eHardware.addContent(eDevice);
			}
			part.addContent(eHardware);
		}
		return part;
	}

	/*
	 * Utworzenie dokumentu z software
	 */
	private Element createSoftwareElements(Element part) {
		// Utworzenie XMLa
		for (Software software : softwares.values()) {
			Element eSoftware = new Element("software-" + software.getId());
			eSoftware.setAttribute("id", software.getId());
			eSoftware.setAttribute("type", software.getType());
			eSoftware.setAttribute("name", software.getName());
			eSoftware.setAttribute("description", software.getDescription());
			eSoftware.setAttribute("date", software.getDate());
			part.addContent(eSoftware);
		}
		return part;
	}

	/*
	 * Utworzenie dokumentu z taskaim crona
	 */
	private Element createCronElements(Element part) {
		// Utworzenie XMLa
		for (CronTask task : tasks) {
			Element eTask = new Element("cron");
			eTask.setAttribute("name", task.getName());
			eTask.setAttribute("patern", task.getPatern());
			eTask.setAttribute("address", task.getAddress().toString());
			eTask.setAttribute("command", task.getCommand().toString());
			eTask.setAttribute("data", task.getData());
			part.addContent(eTask);
		}
		return part;
	}

	/*
	 * Odczyt konfiguracji
	 */
	@SuppressWarnings("unchecked")
	private void read() throws ConfigException {
		try {
			// Sprawdzenie pliku
			if (!new File(Settings.getConfigFilePath()).exists()) {
				return;
			}
			// Odczyt zmapowanych urzadzen
			Document documentConf = new SAXBuilder().build(new File(Settings
					.getConfigFilePath()));
			hardAddress.clear();
			hardSerial.clear();
			devNames.clear();
			for (Element element : (List<Element>) documentConf
					.getRootElement().getChildren("hardware")) {
				readHardwareElements(element);
			}
			// Odczyt oprogramowania
			softwares.clear();
			for (Element element : (List<Element>) documentConf
					.getRootElement().getChildren("software")) {
				readSoftwareElements(element);
			}
			// Odczyt crona
			tasks.clear();
			for (Element element : (List<Element>) documentConf
					.getRootElement().getChildren("cron")) {
				readTasksElements(element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigException("Config file: " + e.getMessage());
		}
	}

	/*
	 * Odczyt hardware
	 */
	@SuppressWarnings("unchecked")
	private void readHardwareElements(Element eHardwares)
			throws HardwareException, ConvertException {
		for (Element eHardware : (List<Element>) eHardwares.getChildren()) {
			// Odczyt hardware
			Hardware hardware = new Hardware(
					eHardware.getAttributeValue("serial"),
					eHardware.getAttributeValue("softwareType"),
					eHardware.getAttributeValue("hardwareType"));
			hardware.setAddress(new Address(eHardware
					.getAttributeValue("address")));
			hardware.setDescription(eHardware.getAttributeValue("description"));
			// Odczyt device dla hardware
			for (Element eDevice : (List<Element>) eHardware
					.getChildren("device")) {
				Device device = new Device(hardware,
						eDevice.getAttributeValue("number"),
						eDevice.getAttributeValue("type"),
						eDevice.getAttributeValue("name"),
						eDevice.getAttributeValue("description"));
				if (device.getName() != null) {
					devNames.put(device.getName(), device);
				}
				hardware.addDevice(device);
			}
			hardAddress.put(hardware.getAddress().getInt(), hardware);
			hardSerial.put(hardware.getSerial(), hardware);
		}
	}

	/*
	 * Odczyt software
	 */
	@SuppressWarnings("unchecked")
	private void readSoftwareElements(Element eSoftwares) {
		for (Element eSoftware : (List<Element>) eSoftwares.getChildren()) {
			// Odczyt hardware
			Software software = new Software(eSoftware.getAttributeValue("id"),
					eSoftware.getAttributeValue("type"),
					eSoftware.getAttributeValue("name"),
					eSoftware.getAttributeValue("description"),
					eSoftware.getAttributeValue("date"));
			softwares.put(software.getId(), software);
		}
	}

	/*
	 * Odczyt task
	 */
	@SuppressWarnings("unchecked")
	private void readTasksElements(Element eTasks) throws ConvertException {
		for (Element eTask : (List<Element>) eTasks.getChildren()) {
			// Odczyt task
			CronTask task = new CronTask(eTask.getAttributeValue("name"),
					eTask.getAttributeValue("patern"),
					eTask.getAttributeValue("address"),
					eTask.getAttributeValue("command"),
					eTask.getAttributeValue("data"));
			tasks.add(task);
		}
	}
}
