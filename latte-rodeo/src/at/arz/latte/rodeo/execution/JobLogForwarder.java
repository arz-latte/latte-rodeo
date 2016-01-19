package at.arz.latte.rodeo.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JobLogForwarder {

	private Thread currentThread;
	private InputStream inputStream;

	public JobLogForwarder(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public synchronized void forwardTo(JobLog log) {
		if (currentThread == null) {
			currentThread = new Thread(createRunnable(log));
			currentThread.setDaemon(true);
			currentThread.start();
		} else {
			throw new IllegalStateException("a thread is currently active");
		}
	}

	public void waitForCompletion() {
		if (currentThread == null) {
			return;
		}
		while (currentThread.isAlive()) {
			try {
				currentThread.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}
		currentThread = null;
	}

	private Runnable createRunnable(final JobLog log) {
		return new Runnable() {

			@Override
			public void run() {
				String encoding = System.getProperty("file.encoding");
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
					try {
						String line = reader.readLine();
						while (line != null) {
							log.log(line);
							line = reader.readLine();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					throw new IllegalStateException("this jvm does not support the current file encoding:" + encoding);
				}
			}
		};
	}

}
