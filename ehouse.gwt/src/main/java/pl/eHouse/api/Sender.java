package pl.eHouse.api;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.api.listeners.IOutputListener;
import pl.eHouse.api.listeners.IResultlListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.Header;
import pl.eHouse.api.message.MessageOut;
import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.MessageOutSerial;
import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.api.serial.SerialAdapter;

public class Sender extends Thread {

	private SerialAdapter serial;
	private List<IOutputListener> listenersOutput;
	private List<IResultlListener> listenersResult;
	private LinkedBlockingQueue<MessageOut> outputQueue;
	private MessageOut message;

	public Sender(SerialAdapter serial, List<IOutputListener> listenersOutput,
			List<IResultlListener> listenersResult) {
		super();
		this.listenersOutput = listenersOutput;
		this.listenersResult = listenersResult;
		this.serial = serial;
		this.outputQueue = new LinkedBlockingQueue<MessageOut>();

	}

	/**
	 * Wstawienie wiadomosci do kolejki
	 * 
	 * @param mess
	 * @throws InterruptedException
	 */
	public void send(MessageOut mess) throws InterruptedException {
		outputQueue.put(mess);
	}

	public synchronized void setMessage(MessageOut message) {
		this.message = message;
	}

	public synchronized MessageOut getMessage(String key) {
		if (message != null && message.getKey().equals(key)) {
			return message;
		} else {
			return null;
		}
	}

	public synchronized MessageOut getMessage() {
		return message;
	}

	public synchronized void removeMessage(String key) {
		if (message != null && message.getKey().equals(key)) {
			message = null;
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				MessageOut mess = outputQueue.take();
				int repeatCount = mess.getRepeatCount();
				int repeatPeriod = mess.getRepeatPeriod();
				setMessage(mess);
				for (int i = 0; i < repeatCount; ++i) {
					mess = getMessage();
					if (mess != null) {
						write(mess);
						waitForAnswer(repeatPeriod);
					} else {
						break;
					}
				}
				if (mess != null) {
					finish(mess);
					setMessage(null);
				}
			} catch (Exception e) {
				Logger.getLogger(Sender.class.getName()).log(Level.SEVERE,
						null, e);
			}
		}

	}

	private void waitForAnswer(int repeatPeriod) throws InterruptedException {
		int period10ms = repeatPeriod / 10;
		for (int i = 0; i < period10ms; ++i) {
			sleep(10);
			if (getMessage() == null) {
				return;
			}
		}
	}

	/**
	 * Zapis wiadomosci do listenerow i seriala
	 * 
	 * @param mess
	 * @throws ListenerException
	 */
	public synchronized void write(MessageOut mess) throws ListenerException {
		List<Integer> data = mess.getToSend();
		// Wyslij do listenerow
		for (Iterator<IOutputListener> iterator = listenersOutput.iterator(); iterator
				.hasNext();) {
			IOutputListener listener = iterator.next();
			if (mess.getType() == Header.ADDRESS) {
				listener.messageOut((MessageOutAddress) mess);
			} else if (mess.getType() == Header.SERIAL) {
				listener.messageOut((MessageOutSerial) mess);
			} else if (mess.getType() == Header.BOOT) {
				listener.messageOut((MessageOutBoot) mess);
			}
		}
		// Wyslij do seriala
		serial.send(mess.getType(),data);
	}

	/**
	 * Przekazanie resultatu wykonanej operacji
	 * 
	 * @param mess
	 */
	private void finish(MessageOut mess) {
		// Rozdzielenie po listenerach
		if (mess.getCommand().isRequest()) {
			for (IResultlListener listener : listenersResult) {
				try {
					if (mess.getType() == Header.ADDRESS) {
						listener.result(new ResultAddress(
								(MessageOutAddress) mess));
					} else if (mess.getType() == Header.SERIAL) {
						listener.result(new ResultSerial(
								(MessageOutSerial) mess));
					} else if (mess.getType() == Header.BOOT) {
						listener.result(new ResultBoot((MessageOutBoot) mess));
					}
				} catch (ListenerException ex) {
					Logger.getLogger(Communicator.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
	}
}
