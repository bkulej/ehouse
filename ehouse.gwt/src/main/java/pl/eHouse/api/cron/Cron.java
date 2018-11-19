package pl.eHouse.api.cron;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.message.Command;
import pl.eHouse.api.message.Id;
import pl.eHouse.api.message.MessageOutAddress;

public class Cron {

	private static SimpleDateFormat format = new SimpleDateFormat("u HH mm");

	private static int PERIOD = 1000 * 60;
	private static Cron cron;
	private Timer timer;

	public void run() {
		timer = new Timer();
		timer.schedule(new CronTimer(), 0, PERIOD);
	}

	public static void start() {
		if (cron == null) {
			cron = new Cron();
			cron.run();
		}
	}

	private class CronTimer extends TimerTask {

		private void send(Address address, Command command, String data) {
			try {
				MessageOutAddress mess = new MessageOutAddress(Id.generate(),
						command, null);
				mess.setAdd(address);
				mess.addDataString(data);
				Communicator.getInstance().sendOut(mess);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				String time = format.format(new Date());
				for (CronTask task : Config.getInstance().getCronTasks()) {
					if (time.matches(task.getPatern())) {
						Logger.getLogger(Cron.class.getName()).info(
								"Cron(" + time + ") - " + task);
						send(task.getAddress(), task.getCommand(),
								task.getData());
					}
				}
			} catch (ConfigException e) {
			}
		}
	}
}
