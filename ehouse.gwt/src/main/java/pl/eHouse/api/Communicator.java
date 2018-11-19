/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.config.SettingsException;
import pl.eHouse.api.cron.Cron;
import pl.eHouse.api.hardware.HardwareException;
import pl.eHouse.api.history.HistoryListener;
import pl.eHouse.api.listeners.IInputListener;
import pl.eHouse.api.listeners.IOutputListener;
import pl.eHouse.api.listeners.IResultlListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.Header;
import pl.eHouse.api.message.MessageIn;
import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.message.MessageInBoot;
import pl.eHouse.api.message.MessageInSerial;
import pl.eHouse.api.message.MessageOut;
import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.MessageOutSerial;
import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.api.serial.SerialAdapter;

/**
 * 
 * @author Bartek
 */
public class Communicator extends Thread {

	private static Communicator communicator;

	private SerialAdapter serial;
	private CopyOnWriteArrayList<IOutputListener> listenersOutput;
	private CopyOnWriteArrayList<IInputListener> listenersInput;
	private CopyOnWriteArrayList<IResultlListener> listenersResult;
	private LinkedBlockingQueue<Integer> inputQueue;
	private Sender sender;

	/**
	 * Konstruktor
	 * 
	 * @param connector
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws SettingsException 
	 */
	private Communicator() throws SecurityException, IOException, SettingsException {
		listenersInput = new CopyOnWriteArrayList<IInputListener>();
		listenersOutput = new CopyOnWriteArrayList<IOutputListener>();
		listenersResult = new CopyOnWriteArrayList<IResultlListener>();
		HistoryListener history = new HistoryListener();
		listenersInput.add(history);
		listenersOutput.add(history);
		inputQueue = new LinkedBlockingQueue<Integer>();
		serial = new SerialAdapter(inputQueue);
		sender = new Sender(serial, listenersOutput, listenersResult);
	}

	/**
	 * Pobranie instancji
	 * 
	 * @return
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws CommunicatorException
	 */
	public static Communicator getInstance() throws CommunicatorException {
		if (communicator == null) {
			try {
				communicator = new Communicator();
				communicator.start();
				communicator.sender.start();
			} catch (Exception e) {
				throw new CommunicatorException(e.getMessage());
			}
		}
		return communicator;
	}

	/**
	 * Zamkniecie instancji
	 * 
	 * @throws CommunicatorException
	 */
	public void disconnect() {
		try {
			serial.disconnect();
		} catch (ListenerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Polaczenie
	 * @throws SettingsException 
	 */
	public void connect(String address, int port) throws ListenerException, SettingsException {
		serial.connect(address, port);
	}

	/**
	 * Dodanie listenera
	 * 
	 * @param listener
	 */
	public void registerListenerInput(IInputListener listener) {
		listenersInput.add(listener);
	}

	/**
	 * Usuniecie listenera
	 * 
	 * @param pListener
	 */
	public void unRegisterListenerInput(IInputListener pListener) {
		for (IInputListener rListener : listenersInput) {
			if (rListener.getId().equals(pListener.getId())) {
				listenersInput.remove(rListener);
				break;
			}
		}
	}

	/**
	 * Dodanie listenera
	 * 
	 * @param listener
	 */
	public void registerListenerOutput(IOutputListener listener) {
		listenersOutput.add(listener);
	}

	/**
	 * Usuniecie listenera
	 * 
	 * @param pListener
	 */
	public void unRegisterListenerOutput(IOutputListener pListener) {
		for (IOutputListener rListener : listenersOutput) {
			if (rListener.getId().equals(pListener.getId())) {
				listenersOutput.remove(rListener);
				break;
			}
		}
	}

	/**
	 * Dodanie listenera
	 * 
	 * @param listener
	 */
	public void registerListenerResult(IResultlListener listener) {
		listenersResult.add(listener);
	}

	/**
	 * Usuniecie listenera
	 * 
	 * @param pListener
	 */
	public void unRegisterListenerResult(IResultlListener pListener) {
		for (IResultlListener rListener : listenersResult) {
			if (rListener.getId().equals(pListener.getId())) {
				listenersResult.remove(rListener);
				break;
			}
		}
	}

	/**
	 * Odczytanie danych z connectora i dystrybucja do listenerow
	 */
	@Override
	public void run() {
		// Uruchomienie crona
		Cron.start();
		
		// Uruchomienie odbierania wiadomosci
		MessageIn messIn = new MessageIn();
		while (true) {
			try {
				Integer part = inputQueue.take();
				// Zalogowanie
				//DataLogger.log(part);
				// Dodanie bajtu do wiadomosci
				messIn.addPart(part);
				if (messIn.isFinished()) {
					// Wyslanie do listenerow
					for (Iterator<IInputListener> iterator = listenersInput
							.iterator(); iterator.hasNext();) {
						IInputListener listener = iterator.next();
						if (messIn.getType() == Header.ADDRESS) {
							listener.messageIn(new MessageInAddress(messIn));
						} else if (messIn.getType() == Header.SERIAL) {
							listener.messageIn(new MessageInSerial(messIn));
						} else if (messIn.getType() == Header.BOOT) {
							listener.messageIn(new MessageInBoot(messIn));
						}
					}
					// Odbierz wiadomosc
					if (messIn.getType() == Header.ADDRESS) {
						receive(new MessageInAddress(messIn));
					} else if (messIn.getType() == Header.SERIAL) {
						receive(new MessageInSerial(messIn));
					} else if (messIn.getType() == Header.BOOT) {
						receive(new MessageInBoot(messIn));
					}
					// Utworz nowa
					messIn = new MessageIn();
				}
			} catch (CommunicatorException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (ConfigException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (HardwareException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (ListenerException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (SettingsException ex) {
				Logger.getLogger(Communicator.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Wyslanie wiadomosci wiele razy
	 * 
	 * @param mess
	 * @param repeat
	 * @param period
	 * @throws CommunicatorException
	 * @throws InterruptedException
	 */
	public synchronized void sendOut(MessageOut mess)
			throws CommunicatorException {
		try {
			sender.send(mess);
		} catch (Exception ex) {
			Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new CommunicatorException(ex.getMessage());
		}
	}

	/**
	 * Wyslanie wiadomosci bezpo�rednio
	 * 
	 * @param mess
	 * @param repeat
	 * @param period
	 * @throws CommunicatorException
	 * @throws InterruptedException
	 */
	public synchronized void writeOut(MessageOut mess)
			throws CommunicatorException {
		try {
			sender.write(mess);
		} catch (Exception ex) {
			Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new CommunicatorException(ex.getMessage());
		}
	}

	/**
	 * Odebranie wiadomosci przychodzacej
	 * 
	 * @param messIn
	 * @throws CommunicatorException 
	 * @throws SettingsException 
	 */
	private void receive(MessageInAddress messIn) throws HardwareException,
			ConfigException, CommunicatorException, SettingsException {
		// Czy wiadomosc dla mnie
		if (messIn.isForMe()) {
			// Znajdz wiadomosc przychodzaca
			MessageOutAddress messOut = (MessageOutAddress) sender
					.getMessage(messIn.getKey());
			if ((messOut != null)
					&& !messOut.getCommand().equals(messIn.getCommand())) {
				// Dekoduj wiadomosc
				ResultAddress result = new ResultAddress(messOut, messIn);
				// Rozdzielenie po listenerach
				for (IResultlListener listener : listenersResult) {
					try {
						listener.result(result);
					} catch (ListenerException ex) {
						Logger.getLogger(Communicator.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
				// Jezeli broadcast to nic nie r�b
				if (!messOut.isBroadcast()) {
					// Zakonczenie wiadomosci
					sender.removeMessage(messIn.getKey());
				}
			}
		}
	}

	/**
	 * Odebranie wiadomosci przychodzącej
	 * 
	 * @param messIn
	 * @throws CommunicatorException
	 */
	private void receive(MessageInSerial messIn) throws HardwareException,
			ConfigException, CommunicatorException {
		// Znajdz wiadomosc przychodzaca
		MessageOutSerial messOut = (MessageOutSerial) sender.getMessage(messIn
				.getKey());
		if ((messOut != null)
				&& !messOut.getCommand().equals(messIn.getCommand())) {
			// Dekoduj wiadomosc
			ResultSerial result = Decoder.decode(messOut, messIn);
			// Rozdzielenie po listenerach
			for (IResultlListener listener : listenersResult) {
				try {
					listener.result(result);
				} catch (ListenerException ex) {
					Logger.getLogger(Communicator.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			// Jezeli broadcast to nic nie r�b
			if (!messOut.isBroadcast()) {
				// Zakonczenie wiadomosci
				sender.removeMessage(messIn.getKey());
			}
		}
	}

	/**
	 * Odebranie wiadomosci przychodzacej
	 * 
	 * @param messIn
	 */
	private void receive(MessageInBoot messIn) throws HardwareException,
			ConfigException {
		// Znajdz wiadomosc przychodzaca
		MessageOutBoot messOut = (MessageOutBoot) sender.getMessage(messIn
				.getKey());
		if ((messOut != null)
				&& !messOut.getCommand().equals(messIn.getCommand())) {
			// Dekoduj wiadomosc
			ResultBoot result = new ResultBoot(messOut, messIn);
			// Rozdzielenie po listenerach
			for (IResultlListener listener : listenersResult) {
				try {
					listener.result(result);
				} catch (ListenerException ex) {
					Logger.getLogger(Communicator.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			// Jezeli broadcast to nic nie r�b
			if (!messOut.isBroadcast()) {
				// Zakonczenie wiadomosci
				sender.removeMessage(messIn.getKey());
			}
		}
	}

}
